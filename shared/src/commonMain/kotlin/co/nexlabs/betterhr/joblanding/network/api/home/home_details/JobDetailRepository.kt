package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest

class JobDetailRepository(private val jobLandingService: JobLandingService) {
    suspend fun getJobDetail(jobId: String) = jobLandingService.getJobDetail(jobId)

    suspend fun saveJob(candidateId: String, jobId: String) = jobLandingService.saveJob(candidateId, jobId)

    suspend fun fetchSaveJobsById(jobId: String) = jobLandingService.fetchSaveJobsById(jobId)

    suspend fun unSaveJob(id: String) = jobLandingService.unSaveJob(id)

    suspend fun checkJobIsApplied(referenceJobId: String, candidateId: String) = jobLandingService.checkJobIsApplied(referenceJobId, candidateId)

    suspend fun requestOTP(phoneNumber: String) = jobLandingService.sendVerification(
        SendVerificationCodeRequest(phoneNumber)
    )

    suspend fun verifyOTP(code: String) = jobLandingService.validateCode(
        VerifyOTPRequest(code)
    )

    suspend fun createCandidate(
        name: String,
        email: String,
        phone: String,
        desiredPosition: String,
        summary: String,
        countryId: String
    ) = jobLandingService.createCandidate(name, email, phone, desiredPosition, summary, countryId)

    suspend fun getBearerToken(token: String) = jobLandingService.getBearerToken(token)

    suspend fun getCandidateData() = jobLandingService.getCandidateDatas()

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
    ) = jobLandingService.createApplication(referenceJobId, subdomain, jobTitle, status, appliedDate, candidateId, currentJobTitle, currentCompany, workingSince, fileIds)

    suspend fun createApplicationWithExistingFileIds(
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
    ) = jobLandingService.createApplicationWithFileExistingIds(referenceJobId, subdomain, jobTitle, status, appliedDate, candidateId, currentJobTitle, currentCompany, workingSince, fileName, files, types, existingFileId)
    suspend fun updateApplication(
        id: String
    ) = jobLandingService.updateApplication(id)

    suspend fun applyJob(
        referenceId: String, candidateId: String, jobId: String, status: String, subDomain: String
    ) = jobLandingService.applyJob(referenceId, candidateId, jobId, status, subDomain)

    suspend fun uploadFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String
    ) = jobLandingService.uploadUserFile(file, fileName, type, candidateId)

    suspend fun updateFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String,
        fileId: String
    ) = jobLandingService.updateUserFile(file, fileName, type, candidateId, fileId)

    suspend fun uploadMultipleFiles(
        files: MutableList<FileUri?>,
        fileNames: MutableList<String?>,
        types: List<String>
    ) = jobLandingService.uploadMultipleFilesForCreateApplication(files, fileNames, types)
}