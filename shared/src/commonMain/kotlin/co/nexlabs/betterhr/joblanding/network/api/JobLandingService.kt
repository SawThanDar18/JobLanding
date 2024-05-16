package co.nexlabs.betterhr.joblanding.network.api

import android.net.Uri
import co.nexlabs.betterhr.job.with_auth.ApplyJobMutation
import co.nexlabs.betterhr.job.with_auth.CandidateQuery
import co.nexlabs.betterhr.job.with_auth.CreateCandidateMutation
import co.nexlabs.betterhr.job.with_auth.FetchApplicationByIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchApplicationQuery
import co.nexlabs.betterhr.job.with_auth.FetchNotificationByIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchNotificationsQuery
import co.nexlabs.betterhr.job.with_auth.FetchSaveJobByJobIdQuery
import co.nexlabs.betterhr.job.with_auth.ResponseAssignmentMutation
import co.nexlabs.betterhr.job.with_auth.ResponseOfferMutation
import co.nexlabs.betterhr.job.with_auth.SaveJobMutation
import co.nexlabs.betterhr.job.with_auth.UnSaveJobMutation
import co.nexlabs.betterhr.job.with_auth.UpdateApplicationMutation
import co.nexlabs.betterhr.job.with_auth.VerifySmsTokenAndAuthMutation
import co.nexlabs.betterhr.job.without_auth.DynamicPagesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionCompaniesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionJobsQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCompanyDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingJobDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingJobListQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingSectionsQuery
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileUploadResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.UploadResponseId
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
        fileName: MutableList<String?>,
        files: MutableList<Uri?>,
        types: List<String>
    ): UploadResponseId

    suspend fun createApplicationWithFileExistingIds(
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
        files: MutableList<Uri?>,
        types: List<String>,
        existingFileId: List<String>,
    ): UploadResponseId

    suspend fun updateApplication(
        id: String
    ): ApolloCall<UpdateApplicationMutation.Data>

    suspend fun uploadUserFile(
        file: Uri,
        fileName: String,
        type: String,
        candidateId: String
    ): UploadResponseId

    suspend fun updateUserFile(
        file: Uri,
        fileName: String,
        type: String,
        candidateId: String,
        fileId: String
    ): UploadResponseId

    suspend fun fetchApplication(
        limit: Int
    ): ApolloCall<FetchApplicationQuery.Data>

    suspend fun getJobLandingJobList(jobIds: List<String>): ApolloCall<JobLandingJobListQuery.Data>

    suspend fun fetchApplicationById(
        id: String
    ): ApolloCall<FetchApplicationByIdQuery.Data>

    suspend fun fetchNotification(
        status: List<String>,
        search: String,
        limit: Int
    ): ApolloCall<FetchNotificationsQuery.Data>

    suspend fun fetchNotificationById(
        id: String
    ): ApolloCall<FetchNotificationByIdQuery.Data>

    suspend fun uploadMultipleFiles(
        files: MutableList<Uri?>,
        fileNames: MutableList<String?>,
        types: List<String>,
        candidateId: String
    ): List<FileUploadResponse>

    suspend fun uploadSingleFile(
        file: Uri,
        fileName: String,
        type: String,
        candidateId: String
    ): FileUploadResponse

    suspend fun responseAssignment(
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
    ): ApolloCall<ResponseAssignmentMutation.Data>

    suspend fun responseOffer(
        id: String,
        note: String,
        status: String,
        responseDate: String,
        attachments: String,
        subDomain: String
    ): ApolloCall<ResponseOfferMutation.Data>

}