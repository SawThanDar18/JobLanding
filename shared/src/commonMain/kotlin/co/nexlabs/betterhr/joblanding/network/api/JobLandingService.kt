package co.nexlabs.betterhr.joblanding.network.api

import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyPhoneNumResponse

interface JobLandingService {

    suspend fun sendVerification(body: SendVerificationCodeRequest): SendVerificationResponse

    suspend fun validateCode(body: VerifyOTPRequest): VerifyPhoneNumResponse

    suspend fun getCountriesList(): GetCountriesListResponse
}