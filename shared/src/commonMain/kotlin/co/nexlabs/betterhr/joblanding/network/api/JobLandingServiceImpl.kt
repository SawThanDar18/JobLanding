package co.nexlabs.betterhr.joblanding.network.api

import android.os.ParcelFileDescriptor
import java.io.FileInputStream
import co.nexlabs.betterhr.job.with_auth.ApplyJobMutation
import co.nexlabs.betterhr.job.with_auth.CandidateQuery
import co.nexlabs.betterhr.job.with_auth.CreateCandidateMutation
import co.nexlabs.betterhr.job.with_auth.CreateCertificateMutation
import co.nexlabs.betterhr.job.with_auth.CreateEducationMutation
import co.nexlabs.betterhr.job.with_auth.CreateExperienceMutation
import co.nexlabs.betterhr.job.with_auth.CreateLanguageMutation
import co.nexlabs.betterhr.job.with_auth.CreateSkillMutation
import co.nexlabs.betterhr.job.with_auth.FetchApplicationByIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchApplicationQuery
import co.nexlabs.betterhr.job.with_auth.FetchInterviewQuery
import co.nexlabs.betterhr.job.with_auth.FetchNotificationByIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchNotificationsQuery
import co.nexlabs.betterhr.job.with_auth.FetchSaveJobByJobIdQuery
import co.nexlabs.betterhr.job.with_auth.ResponseAssignmentMutation
import co.nexlabs.betterhr.job.with_auth.ResponseOfferMutation
import co.nexlabs.betterhr.job.with_auth.SaveJobMutation
import co.nexlabs.betterhr.job.with_auth.ScanWebLogInMutation
import co.nexlabs.betterhr.job.with_auth.UnSaveJobMutation
import co.nexlabs.betterhr.job.with_auth.UpdateApplicationMutation
import co.nexlabs.betterhr.job.with_auth.UpdateCandidateMutation
import co.nexlabs.betterhr.job.with_auth.UpdateCertificateMutation
import co.nexlabs.betterhr.job.with_auth.UpdateEducationMutation
import co.nexlabs.betterhr.job.with_auth.UpdateExperienceMutation
import co.nexlabs.betterhr.job.with_auth.UpdateLanguageMutation
import co.nexlabs.betterhr.job.with_auth.UpdateNotificationMutation
import co.nexlabs.betterhr.job.with_auth.UpdateSkillMutation
import co.nexlabs.betterhr.job.with_auth.VerifySmsTokenAndAuthMutation
import co.nexlabs.betterhr.job.without_auth.DynamicPagesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionCompaniesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionJobsQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCompanyDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCompanyJobsQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingJobDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingJobListQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingSectionsQuery
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.createApolloClient
import co.nexlabs.betterhr.joblanding.createApolloClientWithAuth
import co.nexlabs.betterhr.joblanding.createApolloClientWithAuthWithoutToken
import co.nexlabs.betterhr.joblanding.createHttpClient
import co.nexlabs.betterhr.joblanding.createHttpClientWithAuth
import co.nexlabs.betterhr.joblanding.createHttpClientWithAuthWithoutToken
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.request_response.ExistingFileIdTypeObject
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileTypeObject
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileUploadResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.UploadResponseId
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyPhoneNumResponse
import co.nexlabs.betterhr.joblanding.util.API_KEY
import co.nexlabs.betterhr.joblanding.util.API_VALUE
import co.nexlabs.betterhr.joblanding.util.baseUrl
import co.nexlabs.betterhr.joblanding.util.baseUrlForCreateApplication
import co.nexlabs.betterhr.joblanding.util.baseUrlForMultipleUploadFile
import co.nexlabs.betterhr.joblanding.util.baseUrlForMultipleUploadFileForCreateApplication
import co.nexlabs.betterhr.joblanding.util.baseUrlForUploadFile
import co.nexlabs.betterhr.joblanding.util.getCountriesUrl
import co.nexlabs.betterhr.joblanding.util.smsUrl
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
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
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun initializeApolloClient(): ApolloClient {
    val apolloClient = createHttpClient()
    return createApolloClient()
}

fun initializeApolloClientWithAuth(bearerToken: String): ApolloClient {
    val apolloClient = createHttpClientWithAuth(bearerToken)
    return createApolloClientWithAuth(bearerToken)
}

fun initializeApolloClientWithAuthWithoutToken(): ApolloClient {
    val apolloClient = createHttpClientWithAuthWithoutToken()
    return createApolloClientWithAuthWithoutToken()
}

class JobLandingServiceImpl(private val localStorage: LocalStorage, private val application: Application, private val client: HttpClient): JobLandingService {

    val apolloClient = initializeApolloClient()

    val apolloClientWithAuth = initializeApolloClientWithAuth(localStorage.bearerToken)

    val apolloClientWithAuthWithoutToken = initializeApolloClientWithAuthWithoutToken()

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
        return apolloClientWithAuthWithoutToken.mutation(
            CreateCandidateMutation(
                name, email, phone, desiredPosition, summary, countryId
            )
        )
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
        val response = client.post(getCountriesUrl) {
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
            println("mm>>${response.execute().data!!.dynamicPages}")
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
        isPaginate: Boolean, limit: Int, page: Int
    ): ApolloCall<JobLandingCollectionCompaniesQuery.Data> {
        return apolloClient.query(JobLandingCollectionCompaniesQuery(collectionId, isPaginate, Optional.present(limit), Optional.present(page)))
    }

    override suspend fun getJobLandingCollectionJobs(
        collectionId: String,
        isPaginate: Boolean, limit: Int, page: Int
    ): ApolloCall<JobLandingCollectionJobsQuery.Data> {
        return apolloClient.query(JobLandingCollectionJobsQuery(collectionId, isPaginate, Optional.present(limit), Optional.present(page)))
    }

    override suspend fun getJobDetail(jobId: String): ApolloCall<JobLandingJobDetailQuery.Data> {
        return apolloClient.query(JobLandingJobDetailQuery(jobId))
    }

    override suspend fun getCompanyDetail(companyId: String): ApolloCall<JobLandingCompanyDetailQuery.Data> {
        return apolloClient.query(JobLandingCompanyDetailQuery(companyId))
    }

    override suspend fun getCompanyDetailJob(companyId: String): ApolloCall<JobLandingCompanyJobsQuery.Data> {
        return apolloClient.query(JobLandingCompanyJobsQuery(companyId, false))
    }

    override suspend fun saveJob(
        candidateId: String,
        jobId: String
    ): ApolloCall<SaveJobMutation.Data> {
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
        return apolloClientWithAuth.mutation(
            ApplyJobMutation(
                Optional.present(referenceId),
                candidateId,
                jobId,
                Optional.present(status),
                subDomain
            )
        )
    }

    override suspend fun uploadMultipleFilesForCreateApplication(
        files: MutableList<FileUri?>,
        fileNames: MutableList<String?>,
        types: List<String>
    ): List<FileUploadResponse> {
        val fileTypeObject = FileTypeObject(types)

        var parcelFileDescriptor: ParcelFileDescriptor
        var inputStream: FileInputStream
        var byteArray: ByteArray

        val formData = formData {
            files.forEachIndexed { index, file ->
                parcelFileDescriptor = file?.let { application.contentResolver.openFileDescriptor(it, "r", null) }!!
                inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                byteArray = inputStream.readBytes()

                append("files[]", byteArray, Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${fileNames[index]}")
                })
            }
            append(
                "types",
                Json.encodeToString(fileTypeObject),
                Headers.build {
                    append(HttpHeaders.ContentType, "application/json")
                }
            )
        }

        val response = client.submitFormWithBinaryData(
            url = baseUrlForMultipleUploadFileForCreateApplication,
            formData = formData,
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Uploaded $bytesSentTotal bytes from $contentLength")
            }
            headers {
                append("Authorization", "bearer ${localStorage.bearerToken}")
            }
        }

        return response.body()
    }

    override suspend fun createApplication(
        referenceJobId: String,
        subdomain: String,
        jobTitle: String,
        status: String,
        appliedDate: String,
        candidateId: String,
        currentJobTitle: String,
        currentCompany: String,
        workingSince: String,
//        fileName: MutableList<String?>,
//        files: MutableList<Uri?>,
//        types: List<String>,
        fileIds: List<String>
    ): UploadResponseId {

        val fileIdsTypeObject = ExistingFileIdTypeObject(fileIds)
//        val fileTypeObject = FileTypeObject(types)
//
//        Log.d("file>>>Object", fileTypeObject.toString())

//        var parcelFileDescriptor: ParcelFileDescriptor
//        var inputStream: FileInputStream
//        var byteArray: ByteArray

        val formData = formData {

//            files.forEachIndexed { index, file ->
//                parcelFileDescriptor = file?.let { application.contentResolver.openFileDescriptor(it, "r", null) }!!
//                inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
//                byteArray = inputStream.readBytes()
//
//                append("file$index", byteArray, Headers.build {
//                    append(HttpHeaders.ContentDisposition, "filename=${fileName[index]}")
//                })
//            }
//            append(
//                "file_types",
//                Json.encodeToString(fileTypeObject),
//                Headers.build {
//                    append(HttpHeaders.ContentType, "application/json")
//                }
//            )
            append(
                "file_ids",
                Json.encodeToString(fileIdsTypeObject),
                Headers.build {
                    append(HttpHeaders.ContentType, "application/json")
                }
            )
            append("reference_job_id", referenceJobId)
            append("subdomain", subdomain)
            append("job_title", jobTitle)
            append("status", status)
            append("applied_date", appliedDate)
            append("candidate_id", candidateId)
            append("current_job_title", currentJobTitle)
            append("current_company", currentCompany)
            append("working_since", workingSince)
        }

        /*try {
            val response = client.submitFormWithBinaryData(
                url = baseUrlForCreateApplication,
                formData = formData,
            ) {
                onUpload { bytesSentTotal, contentLength ->
                    println("Uploaded $bytesSentTotal bytes from $contentLength")
                }
                headers {
                    append("Authorization", "bearer ${localStorage.bearerToken}")
                }
            }

            return response.body()
        } catch (e: Exception) {
            Log.d("err>>", e.message.toString())
        }*/
        val response = client.submitFormWithBinaryData(
            url = baseUrlForCreateApplication,
            formData = formData,
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Uploaded $bytesSentTotal bytes from $contentLength")
            }
            headers {
                append("Authorization", "bearer ${localStorage.bearerToken}")
            }
        }

        return response.body()
    }

    override suspend fun createApplicationWithFileExistingIds(
        referenceJobId: String,
        subdomain: String,
        jobTitle: String,
        status: String,
        appliedDate: String,
        candidateId: String,
        currentJobTitle: String,
        currentCompany: String,
        workingSince: String,
        fileName: MutableList<String?>,
        files: MutableList<FileUri?>,
        types: List<String>,
        existingFileId: List<String>
    ): UploadResponseId {
        val fileTypeObject = FileTypeObject(types)
        val existingFileTypeObject = ExistingFileIdTypeObject(existingFileId)

        var parcelFileDescriptor: ParcelFileDescriptor
        var inputStream: FileInputStream
        var byteArray: ByteArray

        val formData = formData {

            files.forEachIndexed { index, file ->
                parcelFileDescriptor = file?.let { application.contentResolver.openFileDescriptor(it, "r", null) }!!
                inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                byteArray = inputStream.readBytes()

                append("file$index", byteArray, Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${fileName[index]}")
                })
            }
            append(
                "file_types",
                Json.encodeToString(fileTypeObject),
                Headers.build {
                    append(HttpHeaders.ContentType, "application/json")
                }
            )
            append(
                "existing_file_ids",
                Json.encodeToString(existingFileTypeObject),
                Headers.build {
                    append(HttpHeaders.ContentType, "application/json")
                }
            )
            append("reference_job_id", referenceJobId)
            append("subdomain", subdomain)
            append("job_title", jobTitle)
            append("status", status)
            append("applied_date", appliedDate)
            append("candidate_id", candidateId)
            append("current_job_title", currentJobTitle)
            append("current_company", currentCompany)
            append("working_since", workingSince)
        }

        try {
            val response = client.submitFormWithBinaryData(
                url = baseUrlForCreateApplication,
                formData = formData,
            ) {
                onUpload { bytesSentTotal, contentLength ->
                    println("Uploaded $bytesSentTotal bytes from $contentLength")
                }
                headers {
                    append("Authorization", "bearer ${localStorage.bearerToken}")
                }
            }

            return response.body()
        } catch (e: Exception) {
        }
        val response = client.submitFormWithBinaryData(
            url = baseUrlForCreateApplication,
            formData = formData,
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Uploaded $bytesSentTotal bytes from $contentLength")
            }
            headers {
                append("Authorization", "bearer ${localStorage.bearerToken}")
            }
        }

        return response.body()
    }

    override suspend fun updateApplication(
        id: String
    ): ApolloCall<UpdateApplicationMutation.Data> {
        return apolloClientWithAuth.mutation(UpdateApplicationMutation(id, "applied", "2021-05-02", "title"))
    }

    override suspend fun uploadUserFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String,
    ): FileUploadResponse {
        val parcelFileDescriptor = application.contentResolver.openFileDescriptor(file, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val byteArray = inputStream.readBytes()

        val formData = formData {
            append("file", byteArray, Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=\"${fileName.replace(" ", "_")}\"")
            })
            append("type", type)
            append("candidate_id", candidateId)
        }

        val response = client.submitFormWithBinaryData(
            url = baseUrlForUploadFile,
            formData = formData,
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Uploaded $bytesSentTotal bytes from $contentLength")
            }
            headers {
                append("Authorization", "bearer ${localStorage.bearerToken}")
            }
        }

        return response.body()
    }

    override suspend fun updateUserFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String,
        fileId: String
    ): FileUploadResponse {
        val parcelFileDescriptor = application.contentResolver.openFileDescriptor(file, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val byteArray = inputStream.readBytes()

        val formData = formData {
            append("file", byteArray, Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=\"${fileName.replace(" ", "_")}\"")
            })
            append("type", type)
            append("candidate_id", candidateId)
            append("fileId", fileId)
        }

        val response = client.submitFormWithBinaryData(
            url = baseUrlForUploadFile,
            formData = formData,
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Uploaded $bytesSentTotal bytes from $contentLength")
            }
            headers {
                append("Authorization", "bearer ${localStorage.bearerToken}")
            }
        }

        return response.body()
    }

    override suspend fun fetchApplication(limit: Int): ApolloCall<FetchApplicationQuery.Data> {
        return apolloClientWithAuth.query(FetchApplicationQuery( Optional.present(null), limit, Optional.present("")))
    }

    override suspend fun getJobLandingJobList(jobIds: List<String>): ApolloCall<JobLandingJobListQuery.Data> {
        return apolloClient.query(JobLandingJobListQuery(jobIds))
    }

    override suspend fun fetchApplicationById(id: String): ApolloCall<FetchApplicationByIdQuery.Data> {
        return apolloClientWithAuth.query(FetchApplicationByIdQuery(id))
    }

    override suspend fun fetchNotification(
        status: List<String>,
        search: String,
        limit: Int
    ): ApolloCall<FetchNotificationsQuery.Data> {
        return apolloClientWithAuth.query(FetchNotificationsQuery(Optional.present(status), Optional.present(search), limit))
    }

    override suspend fun fetchNotificationById(id: String): ApolloCall<FetchNotificationByIdQuery.Data> {
        return apolloClientWithAuth.query(FetchNotificationByIdQuery(id))
    }

    override suspend fun uploadMultipleFiles(
        files: MutableList<FileUri?>,
        fileNames: MutableList<String?>,
        types: List<String>,
        candidateId: String,
        referenceId: String
    ): List<FileUploadResponse> {
        val fileTypeObject = FileTypeObject(types)

        var parcelFileDescriptor: ParcelFileDescriptor
        var inputStream: FileInputStream
        var byteArray: ByteArray

        val formData = formData {
            files.forEachIndexed { index, file ->
                parcelFileDescriptor = file?.let { application.contentResolver.openFileDescriptor(it, "r", null) }!!
                inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                byteArray = inputStream.readBytes()

                append("files[]", byteArray, Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${fileNames[index]}")
                })
            }
            append(
                "types",
                Json.encodeToString(fileTypeObject),
                Headers.build {
                    append(HttpHeaders.ContentType, "application/json")
                }
            )
            append("candidate_id", candidateId)
            append("reference_id", referenceId)
        }

        val response = client.submitFormWithBinaryData(
            url = baseUrlForMultipleUploadFile,
            formData = formData,
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Uploaded $bytesSentTotal bytes from $contentLength")
            }
            headers {
                append("Authorization", "bearer ${localStorage.bearerToken}")
            }
        }

        return response.body()
    }

    override suspend fun uploadSingleFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String,
        referenceId: String
    ): FileUploadResponse {
        val parcelFileDescriptor = application.contentResolver.openFileDescriptor(file, "r", null)
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val byteArray = inputStream.readBytes()

        val formData = formData {
            append("file", byteArray, Headers.build {
                append(HttpHeaders.ContentDisposition, "filename=\"${fileName.replace(" ", "_")}\"")
            })
            append("type", type)
            append("candidate_id", candidateId)
            append("reference_id", referenceId)
        }

        val response = client.submitFormWithBinaryData(
            url = baseUrlForUploadFile,
            formData = formData,
        ) {
            onUpload { bytesSentTotal, contentLength ->
                println("Uploaded $bytesSentTotal bytes from $contentLength")
            }
            headers {
                append("Authorization", "bearer ${localStorage.bearerToken}")
            }
        }

        return response.body()
    }

    override suspend fun responseAssignment(
        candidateId: String,
        jobId: String,
        referenceId: String,
        title: String,
        description: String,
        status: String,
        summitedDate: String,
        candidateDescription: String,
        endTime: String,
        attachments: String,
        subDomain: String,
        referenceApplicationId: String
    ): ApolloCall<ResponseAssignmentMutation.Data> {
        return apolloClientWithAuth.mutation(ResponseAssignmentMutation(
            Optional.present(candidateId),
            Optional.present(jobId),
            Optional.present(referenceId),
            Optional.present(title),
            Optional.present(description),
            Optional.present(status),
            Optional.present(summitedDate),
            Optional.present(candidateDescription),
            Optional.present(endTime),
            Optional.present(attachments),
            subDomain,
            Optional.present(referenceApplicationId)
        ))
    }

    override suspend fun responseOffer(
        id: String,
        note: String,
        status: String,
        responseDate: String,
        attachments: String,
        subDomain: String
    ): ApolloCall<ResponseOfferMutation.Data> {
        return apolloClientWithAuth.mutation(ResponseOfferMutation(
            Optional.present(id),
            Optional.present(note),
            Optional.present(status),
            Optional.present(responseDate),
            Optional.present(attachments),
            subDomain
        ))
    }

    override suspend fun updateNotification(id: String, status: String, isSigned: Boolean?): ApolloCall<UpdateNotificationMutation.Data> {
        return apolloClientWithAuth.mutation(
            UpdateNotificationMutation(
                id,
                Optional.present(status),
                Optional.present(isSigned)
            )
        )
    }

    override suspend fun updateSummary(id: String, summary: String): ApolloCall<UpdateCandidateMutation.Data> {
        return apolloClientWithAuth.mutation(UpdateCandidateMutation(id, Optional.present(summary)))
    }

    override suspend fun createExperience(
        position: String,
        candidateId: String,
        companyId: String,
        title: String,
        location: String,
        experienceLevel: String,
        employmentType: String,
        startDate: String,
        endDate: String,
        isCurrentJob: Boolean,
        description: String
    ): ApolloCall<CreateExperienceMutation.Data> {
        return apolloClientWithAuth.mutation(CreateExperienceMutation(
            Optional.present(position),
            Optional.present(candidateId),
            Optional.present(companyId),
            Optional.present(title),
            Optional.present(location),
            Optional.present(experienceLevel),
            Optional.present(employmentType),
            Optional.present(startDate),
            Optional.present(endDate),
            Optional.present(isCurrentJob),
            Optional.present(description),
        ))
    }

    override suspend fun updateExperience(
        id: String,
        candidateId: String,
        companyId: String,
        title: String,
        location: String,
        experienceLevel: String,
        employmentType: String,
        startDate: String,
        endDate: String,
        isCurrentJob: Boolean,
        description: String
    ): ApolloCall<UpdateExperienceMutation.Data> {
        return apolloClientWithAuth.mutation(UpdateExperienceMutation(id,
        Optional.present(candidateId),
            Optional.present(companyId),
            Optional.present(title),
            Optional.present(location),
            Optional.present(experienceLevel),
            Optional.present(employmentType),
            Optional.present(startDate),
            Optional.present(endDate),
            Optional.present(isCurrentJob),
            Optional.present(description),
        ))
    }

    override suspend fun createEducation(
        countryName: String,
        institution: String,
        educationLevel: String,
        degree: String,
        fieldOfStudy: String,
        startDate: String,
        endDate: String,
        isCurrentStudy: Boolean,
        description: String,
        candidateId: String
    ): ApolloCall<CreateEducationMutation.Data> {
        return apolloClientWithAuth.mutation(CreateEducationMutation(
            Optional.present(countryName),
            Optional.present(institution),
            Optional.present(educationLevel),
            Optional.present(degree),
            Optional.present(fieldOfStudy),
            Optional.present(startDate),
            Optional.present(endDate),
            Optional.present(isCurrentStudy),
            Optional.present(description),
            Optional.present(candidateId),
        ))
    }

    override suspend fun updateEducation(
        id: String,
        countryName: String,
        institution: String,
        educationLevel: String,
        degree: String,
        fieldOfStudy: String,
        startDate: String,
        endDate: String,
        isCurrentStudy: Boolean,
        description: String
    ): ApolloCall<UpdateEducationMutation.Data> {
        return apolloClientWithAuth.mutation(UpdateEducationMutation(
            id,
            Optional.present(countryName),
            Optional.present(institution),
            Optional.present(educationLevel),
            Optional.present(degree),
            Optional.present(fieldOfStudy),
            Optional.present(startDate),
            Optional.present(endDate),
            Optional.present(isCurrentStudy),
            Optional.present(description),
        ))
    }

    override suspend fun createLanguage(
        name: String,
        proficiencyLevel: String,
        candidateId: String
    ): ApolloCall<CreateLanguageMutation.Data> {
        return apolloClientWithAuth.mutation(CreateLanguageMutation(
            Optional.present(name),
            Optional.present(proficiencyLevel),
            Optional.present(candidateId),
        ))
    }

    override suspend fun updateLanguage(
        id: String,
        name: String,
        proficiencyLevel: String
    ): ApolloCall<UpdateLanguageMutation.Data> {
        return apolloClientWithAuth.mutation(UpdateLanguageMutation(
            id,
            Optional.present(name),
            Optional.present(proficiencyLevel),
        ))
    }

    override suspend fun createSkill(
        name: String,
        candidateId: String
    ): ApolloCall<CreateSkillMutation.Data> {
        return apolloClientWithAuth.mutation(CreateSkillMutation(
            name, candidateId
        ))
    }

    override suspend fun updateSkill(
        id: String,
        name: String
    ): ApolloCall<UpdateSkillMutation.Data> {
        return apolloClientWithAuth.mutation(UpdateSkillMutation(
            id, name
        ))
    }

    override suspend fun createCertification(
        candidateId: String,
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ): ApolloCall<CreateCertificateMutation.Data> {
        return apolloClientWithAuth.mutation(CreateCertificateMutation(
            Optional.present(candidateId),
            Optional.present(courseName),
            Optional.present(issuingOrganization),
            Optional.present(issueDate),
            Optional.present(expireDate),
            Optional.present(isExpire),
            Optional.present(credentialUrl)
        ))
    }

    override suspend fun updateCertification(
        id: String,
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ): ApolloCall<UpdateCertificateMutation.Data> {
        return apolloClientWithAuth.mutation(UpdateCertificateMutation(
            id,
            Optional.present(courseName),
            Optional.present(issuingOrganization),
            Optional.present(issueDate),
            Optional.present(expireDate),
            Optional.present(isExpire),
            Optional.present(credentialUrl),
        ))
    }

    override suspend fun fetchInterview(
        limit: Int,
        page: Int
    ): ApolloCall<FetchInterviewQuery.Data> {
        return apolloClientWithAuth.query(FetchInterviewQuery(limit, page))
    }

    override suspend fun scanWebLogIn(qrToken: String): ApolloCall<ScanWebLogInMutation.Data> {
        return apolloClientWithAuth.mutation(ScanWebLogInMutation(qrToken))
    }
}




/*val headerInterceptor = Interceptor { chain ->
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
    .build()*/