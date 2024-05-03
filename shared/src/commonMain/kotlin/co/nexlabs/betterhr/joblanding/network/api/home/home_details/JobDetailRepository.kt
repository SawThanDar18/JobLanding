package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest

class JobDetailRepository(private val jobLandingService: JobLandingService) {
    suspend fun getJobDetail(jobId: String) = jobLandingService.getJobDetail(jobId)

    suspend fun saveJob(candidateId: String, jobId: String) = jobLandingService.saveJob(candidateId, jobId)

    suspend fun fetchSaveJobsById(jobId: String) = jobLandingService.fetchSaveJobsById(jobId)

    suspend fun unSaveJob(id: String) = jobLandingService.unSaveJob(id)

    suspend fun requestOTP(phoneNumber: String) = jobLandingService.sendVerification(
        SendVerificationCodeRequest(phoneNumber)
    )

    suspend fun verifyOTP(code: String) = jobLandingService.validateCode(
        VerifyOTPRequest(code)
    )

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
        files: List<FileRequest>
    ) = jobLandingService.createApplication(referenceJobId, subdomain, jobTitle, status, appliedDate, candidateId, currentJobTitle, currentCompany, workingSince, files)
}