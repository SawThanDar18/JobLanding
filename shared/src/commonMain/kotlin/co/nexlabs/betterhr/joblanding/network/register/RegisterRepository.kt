package co.nexlabs.betterhr.joblanding.network.register

import co.nexlabs.betterhr.joblanding.network.sms.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.sms.SmsService
import co.nexlabs.betterhr.joblanding.network.sms.VerifyOTPRequest

class RegisterRepository(private val smsService: SmsService) {

    suspend fun requestOTP(phoneNumber: String) = smsService.sendVerification(
        SendVerificationCodeRequest(phoneNumber)
    )

    suspend fun verifyOTP(code: String) = smsService.validateCode(
        VerifyOTPRequest(code)
    )
}