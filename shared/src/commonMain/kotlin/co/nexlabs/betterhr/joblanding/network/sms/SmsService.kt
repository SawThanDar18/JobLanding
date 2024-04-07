package co.nexlabs.betterhr.joblanding.network.sms

interface SmsService {

    suspend fun sendVerification(body: SendVerificationCodeRequest): SendVerificationResponse

    suspend fun validateCode(body: VerifyOTPRequest): VerifyPhoneNumResponse
}