package co.nexlabs.betterhr.joblanding.network.api

import co.nexlabs.betterhr.job.with_auth.ApplyJobMutation
import co.nexlabs.betterhr.job.with_auth.CandidateQuery
import co.nexlabs.betterhr.job.with_auth.CheckJobIsApplyQuery
import co.nexlabs.betterhr.job.with_auth.CreateCandidateMutation
import co.nexlabs.betterhr.job.with_auth.CreateCertificateMutation
import co.nexlabs.betterhr.job.with_auth.CreateCompanyMutation
import co.nexlabs.betterhr.job.with_auth.CreateEducationMutation
import co.nexlabs.betterhr.job.with_auth.CreateExperienceMutation
import co.nexlabs.betterhr.job.with_auth.CreateLanguageMutation
import co.nexlabs.betterhr.job.with_auth.CreatePositionMutation
import co.nexlabs.betterhr.job.with_auth.CreateSkillMutation
import co.nexlabs.betterhr.job.with_auth.FetchApplicationByIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchApplicationQuery
import co.nexlabs.betterhr.job.with_auth.FetchInterviewQuery
import co.nexlabs.betterhr.job.with_auth.FetchNotificationByIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchNotificationsQuery
import co.nexlabs.betterhr.job.with_auth.FetchSaveJobByJobIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchSavedJobsIdsQuery
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
import co.nexlabs.betterhr.job.with_auth.UpdateSummaryMutation
import co.nexlabs.betterhr.job.with_auth.VerifySmsTokenAndAuthMutation
import co.nexlabs.betterhr.job.with_auth.type.File
import co.nexlabs.betterhr.job.without_auth.DynamicPagesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionCompaniesQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionJobsQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCompanyDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingCompanyJobsQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingJobDetailQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingJobListQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingSavedJobsQuery
import co.nexlabs.betterhr.job.without_auth.JobLandingSectionsQuery
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileUploadResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.UploadResponseId
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyPhoneNumResponse
import com.apollographql.apollo3.ApolloCall
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

    suspend fun getJobLandingCollectionCompanies(collectionId: String, isPaginate: Boolean, limit: Int, page: Int): ApolloCall<JobLandingCollectionCompaniesQuery.Data>

    suspend fun getJobLandingCollectionJobs(collectionId: String, isPaginate: Boolean, limit: Int, page: Int): ApolloCall<JobLandingCollectionJobsQuery.Data>

    suspend fun getJobDetail(jobId: String): ApolloCall<JobLandingJobDetailQuery.Data>

    suspend fun getCompanyDetail(companyId: String): ApolloCall<JobLandingCompanyDetailQuery.Data>

    suspend fun getCompanyDetailJob(companyId: String): ApolloCall<JobLandingCompanyJobsQuery.Data>

    suspend fun saveJob(candidateId: String, jobId: String): ApolloCall<SaveJobMutation.Data>

    suspend fun fetchSaveJobsById(jobId: String): ApolloCall<FetchSaveJobByJobIdQuery.Data>

    suspend fun unSaveJob(id: String): ApolloCall<UnSaveJobMutation.Data>

    suspend fun checkJobIsApplied(referenceJobId: String, candidateId: String): ApolloCall<CheckJobIsApplyQuery.Data>

    suspend fun applyJob(referenceId: String, candidateId: String, jobId: String, status: String, subDomain: String): ApolloCall<ApplyJobMutation.Data>

    suspend fun uploadMultipleFilesForCreateApplication(
        files: MutableList<FileUri?>,
        fileNames: MutableList<String?>,
        types: List<String>
    ): List<FileUploadResponse>

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
//        fileName: MutableList<String?>,
//        files: MutableList<Uri?>,
//        types: List<String>,
        fileIds: List<String>
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
        files: MutableList<FileUri?>,
        types: List<String>,
        existingFileId: List<String>,
    ): UploadResponseId

    suspend fun updateApplication(
        id: String
    ): ApolloCall<UpdateApplicationMutation.Data>

    suspend fun uploadUserFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String
    ): FileUploadResponse

    suspend fun updateUserFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String,
        fileId: String
    ): FileUploadResponse

    suspend fun fetchApplication(
        limit: Int, status: List<String>
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
        files: MutableList<FileUri?>,
        fileNames: MutableList<String?>,
        types: List<String>,
        candidateId: String,
        referenceId: String
    ): List<FileUploadResponse>

    suspend fun uploadSingleFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String,
        referenceId: String
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

    suspend fun updateNotification(
        id: String,
        status: String,
        isSigned: Boolean?
    ): ApolloCall<UpdateNotificationMutation.Data>

    suspend fun updateSummary(
        id: String,
        summary: String
    ): ApolloCall<UpdateSummaryMutation.Data>

    suspend fun createPosition(positionName: String): ApolloCall<CreatePositionMutation.Data>

    suspend fun createExperience(
        positionId: String,
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
    ): ApolloCall<CreateExperienceMutation.Data>

    suspend fun updateExperience(
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
    ): ApolloCall<UpdateExperienceMutation.Data>

    suspend fun createEducation(
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
    ): ApolloCall<CreateEducationMutation.Data>

    suspend fun updateEducation(
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
    ): ApolloCall<UpdateEducationMutation.Data>

    suspend fun createLanguage(
        name: String,
        proficiencyLevel: String,
        candidateId: String
    ): ApolloCall<CreateLanguageMutation.Data>

    suspend fun updateLanguage(
        id: String,
        name: String,
        proficiencyLevel: String
    ): ApolloCall<UpdateLanguageMutation.Data>

    suspend fun createSkill(
        name: String,
        candidateId: String
    ): ApolloCall<CreateSkillMutation.Data>

    suspend fun updateSkill(
        id: String,
        name: String
    ): ApolloCall<UpdateSkillMutation.Data>

    suspend fun createCertification(
        candidateId: String,
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ): ApolloCall<CreateCertificateMutation.Data>

    suspend fun updateCertification(
        id: String,
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ): ApolloCall<UpdateCertificateMutation.Data>

    suspend fun fetchInterview(
        limit: Int, page: Int
    ): ApolloCall<FetchInterviewQuery.Data>

    suspend fun scanWebLogIn(qrToken: String): ApolloCall<ScanWebLogInMutation.Data>

    suspend fun getSavedJobsIds(): ApolloCall<FetchSavedJobsIdsQuery.Data>

    suspend fun getSavedJobs(jobsId: List<String>): ApolloCall<JobLandingSavedJobsQuery.Data>

    suspend fun createCompany(companyName: String, candidateId: String, fileIds: String): ApolloCall<CreateCompanyMutation.Data>

}