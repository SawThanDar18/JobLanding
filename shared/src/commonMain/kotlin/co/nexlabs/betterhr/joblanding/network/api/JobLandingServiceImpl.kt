package co.nexlabs.betterhr.joblanding.network.api

import android.app.Application
import android.util.Log
import co.nexlabs.betterhr.job.with_auth.ApplyJobMutation
import co.nexlabs.betterhr.job.with_auth.CandidateQuery
import co.nexlabs.betterhr.job.with_auth.CreateCandidateMutation
import co.nexlabs.betterhr.job.with_auth.FetchSaveJobByJobIdQuery
import co.nexlabs.betterhr.job.with_auth.SaveJobMutation
import co.nexlabs.betterhr.job.with_auth.UnSaveJobMutation
import co.nexlabs.betterhr.job.with_auth.VerifySmsTokenAndAuthMutation
import co.nexlabs.betterhr.job.without_auth.DynamicPagesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionCompaniesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionJobsQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCompanyDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingJobDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingSectionsQuery
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyPhoneNumResponse
import co.nexlabs.betterhr.joblanding.util.API_KEY
import co.nexlabs.betterhr.joblanding.util.API_VALUE
import co.nexlabs.betterhr.joblanding.util.API_VALUE_JOB
import co.nexlabs.betterhr.joblanding.util.baseUrl
import co.nexlabs.betterhr.joblanding.util.baseUrlForAuth
import co.nexlabs.betterhr.joblanding.util.baseUrlForJob
import co.nexlabs.betterhr.joblanding.util.getCountriesUrl
import co.nexlabs.betterhr.joblanding.util.smsUrl
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.exception.ApolloException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.*
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import com.apollographql.apollo3.network.okHttpClient
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class JobLandingServiceImpl(private val application: Application, private val client: HttpClient) : JobLandingService {

    private val localStorage: LocalStorage
    init {
        localStorage = AndroidLocalStorageImpl(application)
    }

    val headerInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader(API_KEY, API_VALUE_JOB)
            .build()
        chain.proceed(request)
    }

    val headerInterceptorWithAuth = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "bearer ${localStorage.bearerToken}")
            .build()
        chain.proceed(request)
    }

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        val logger = HttpLoggingInterceptor.Logger.DEFAULT
        level = HttpLoggingInterceptor.Level.HEADERS
        HttpLoggingInterceptor.Logger { message -> println("message>>$logger") }
    }

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptor)
        .build()

    val okHttpClientWithAuth = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headerInterceptorWithAuth)
        .build()

    val apolloClient = ApolloClient.Builder()
        .okHttpClient(okHttpClient)
        .serverUrl(baseUrlForJob)
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()

    val apolloClientWithAuth = ApolloClient.Builder()
        .okHttpClient(okHttpClientWithAuth)
        .serverUrl(baseUrlForAuth)
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()

    val apolloClientWithAuthWithoutToken = ApolloClient.Builder()
        .serverUrl(baseUrlForAuth)
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()

    override suspend fun sendVerification(body: SendVerificationCodeRequest): SendVerificationResponse {

        val mutation = """
        mutation SendVerificationCode(${'$'}phone: String!) {
            sendVerificationCode(phone: ${'$'}phone) {
                status
                message
                token
            }
        }
    """.trimIndent()

        val variables = mapOf("phone" to body.phone)

        val requestBody = buildJsonObject {
            put("query", mutation)
            put("variables", buildJsonObject {
                variables.forEach { (key, value) ->
                    put(key, JsonPrimitive(value))
                }
            })
        }

        var response = client.post("${baseUrl}${smsUrl}") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(requestBody.toString())
        }

        return response.body()
    }

    override suspend fun validateCode(body: VerifyOTPRequest): VerifyPhoneNumResponse {

        val mutation = """
        mutation VerifyPhoneNumber(${'$'}code: String!) {
            verifyPhoneNumber(code: ${'$'}code) {
                status
                message
                token
            }
        }
    """.trimIndent()

        val variables = mapOf("code" to body.code)

        val requestBody = buildJsonObject {
            put("query", mutation)
            put("variables", buildJsonObject {
                variables.forEach { (key, value) ->
                    put(key, JsonPrimitive(value))
                }
            })
        }

        var response = client.post("${baseUrl}${smsUrl}") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(requestBody.toString())
        }

        return response.body()
    }

    override suspend fun createCandidate(
        name: String,
        email: String,
        phone: String,
        desiredPosition: String,
        summary: String,
        countryId: String
    ): ApolloCall<CreateCandidateMutation.Data> {
        return apolloClientWithAuthWithoutToken.mutation(CreateCandidateMutation(
            name, email, phone, desiredPosition, summary, countryId
        ))
    }

    override suspend fun getBearerToken(token: String): ApolloCall<VerifySmsTokenAndAuthMutation.Data> {
        /*try {
            val response = apolloClientWithAuthWithoutToken.mutation(VerifySmsTokenAndAuthMutation(token))
            println("mm>>${response.execute().data!!.verifySmsTokenAndAuth.token}")
        } catch (e: ApolloException) {
            println("ApolloClient error: ${e.message}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }*/
        return apolloClientWithAuthWithoutToken.mutation(VerifySmsTokenAndAuthMutation(token))
    }

    override suspend fun getCandidateDatas(): ApolloCall<CandidateQuery.Data> {
        /*try {
            val response = apolloClientWithAuth.query(CandidateQuery())
            println("mm>>${response.execute().data!!.me.id}")
        } catch (e: ApolloException) {
            println("ApolloClient error: ${e.message}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }*/
        return apolloClientWithAuth.query(CandidateQuery())
    }

    override suspend fun getCountriesList(): GetCountriesListResponse {
        val response = client.post("${baseUrl}${getCountriesUrl}") {
            headers {
                append(API_KEY, API_VALUE)
            }
        }
        return response.body()
    }

    override suspend fun getDynamicPagesQuery(
        countryId: String,
        platform: String
    ): ApolloCall<DynamicPagesQuery.Data> {

        /*try {
            val response = apolloClient.query(DynamicPagesQuery(countryId, platform))
            println("mm>>${response.execute().data!!.dynamicPages.size}")
        } catch (e: ApolloException) {
            println("ApolloClient error: ${e.message}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }*/
        return apolloClient.query(DynamicPagesQuery(countryId, platform))
    }

    override suspend fun getJobLandingSections(pageId: String): ApolloCall<JobLandingSectionsQuery.Data> {
        return apolloClient.query(JobLandingSectionsQuery(pageId))
    }

    override suspend fun getJobLandingCollectionCompanies(
        collectionId: String,
        isPaginate: Boolean
    ): ApolloCall<JobLandingCollectionCompaniesQuery.Data> {
        return apolloClient.query(JobLandingCollectionCompaniesQuery(collectionId, isPaginate))
    }

    override suspend fun getJobLandingCollectionJobs(collectionId: String, isPaginate: Boolean): ApolloCall<JobLandingCollectionJobsQuery.Data> {
        return apolloClient.query(JobLandingCollectionJobsQuery(collectionId, isPaginate))
    }

    override suspend fun getJobDetail(jobId: String): ApolloCall<JobLandingJobDetailQuery.Data> {
        return apolloClient.query(JobLandingJobDetailQuery(jobId))
    }

    override suspend fun getCompanyDetail(companyId: String): ApolloCall<JobLandingCompanyDetailQuery.Data> {
        return apolloClient.query(JobLandingCompanyDetailQuery(companyId))
    }

    override suspend fun saveJob(
        candidateId: String,
        jobId: String
    ): ApolloCall<SaveJobMutation.Data> {
        Log.d("candi>>>", candidateId)
        /*try {
            val response = apolloClientWithAuth.mutation(SaveJobMutation(candidateId, jobId))
            println("mm>>${response.execute().data!!.saveJob!!.job_id}")
        } catch (e: ApolloException) {
            println("ApolloClient error: ${e.message}")
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }*/
        return apolloClientWithAuth.mutation(SaveJobMutation(candidateId, jobId))
    }

    override suspend fun fetchSaveJobsById(jobId: String): ApolloCall<FetchSaveJobByJobIdQuery.Data> {
        return apolloClientWithAuth.query(FetchSaveJobByJobIdQuery(jobId))
    }

    override suspend fun unSaveJob(id: String): ApolloCall<UnSaveJobMutation.Data> {
        return apolloClientWithAuth.mutation(UnSaveJobMutation(id))
    }

    override suspend fun applyJob(
        referenceId: String,
        candidateId: String,
        jobId: String,
        status: String,
        subDomain: String
    ): ApolloCall<ApplyJobMutation.Data> {
        return apolloClientWithAuth.mutation(ApplyJobMutation(Optional.present(referenceId), candidateId, jobId, Optional.present(status), subDomain))
    }
}
