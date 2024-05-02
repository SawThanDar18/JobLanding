package co.nexlabs.betterhr.joblanding.network.register

import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest

class RegisterRepository(private val jobLandingService: JobLandingService) {

    suspend fun requestOTP(phoneNumber: String) = jobLandingService.sendVerification(
        SendVerificationCodeRequest(phoneNumber)
    )

    suspend fun verifyOTP(code: String) = jobLandingService.validateCode(
        VerifyOTPRequest(code)
    )

    suspend fun getCandidateData() = jobLandingService.getCandidateDatas()
}