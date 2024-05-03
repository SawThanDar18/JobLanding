package co.nexlabs.betterhr.joblanding.network.api

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
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyPhoneNumResponse
import com.apollographql.apollo3.ApolloCall
import java.io.File

interface JobLandingService {

    suspend fun sendVerification(body: SendVerificationCodeRequest): SendVerificationResponse

    suspend fun validateCode(body: VerifyOTPRequest): VerifyPhoneNumResponse

    suspend fun createCandidate(
        name: String,
        email: String,
        phone: String,
        desiredPosition: String,
        summary: String,
        countryId: String
    ): ApolloCall<CreateCandidateMutation.Data>

    suspend fun getBearerToken(token: String): ApolloCall<VerifySmsTokenAndAuthMutation.Data>

    suspend fun getCandidateDatas(): ApolloCall<CandidateQuery.Data>

    suspend fun getCountriesList(): GetCountriesListResponse

    suspend fun getDynamicPagesQuery(countryId: String, platform: String): ApolloCall<DynamicPagesQuery.Data>

    suspend fun getJobLandingSections(pageId: String): ApolloCall<JobLandingSectionsQuery.Data>

    suspend fun getJobLandingCollectionCompanies(collectionId: String, isPaginate: Boolean): ApolloCall<JobLandingCollectionCompaniesQuery.Data>

    suspend fun getJobLandingCollectionJobs(collectionId: String, isPaginate: Boolean): ApolloCall<JobLandingCollectionJobsQuery.Data>

    suspend fun getJobDetail(jobId: String): ApolloCall<JobLandingJobDetailQuery.Data>

    suspend fun getCompanyDetail(companyId: String): ApolloCall<JobLandingCompanyDetailQuery.Data>

    suspend fun saveJob(candidateId: String, jobId: String): ApolloCall<SaveJobMutation.Data>

    suspend fun fetchSaveJobsById(jobId: String): ApolloCall<FetchSaveJobByJobIdQuery.Data>

    suspend fun unSaveJob(id: String): ApolloCall<UnSaveJobMutation.Data>

    suspend fun applyJob(referenceId: String, candidateId: String, jobId: String, status: String, subDomain: String): ApolloCall<ApplyJobMutation.Data>

    suspend fun createApplication(
        referenceJobId: String,
        subdomain: String,
        jobTitle: String,
        status: String,
        appliedDate: String,
        candidateId: String,
        currentJobTitle: String,
        currentCompany: String,
        workingSince: String,
        files: List<FileRequest>
    ): String

}