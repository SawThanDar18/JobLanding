package co.nexlabs.betterhr.joblanding.android.data

import co.nexlabs.betterhr.joblanding.network.sms.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.sms.SmsServiceImpl
import co.nexlabs.betterhr.joblanding.network.sms.VerifyOTPRequest
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

fun exampleUsage() = runBlocking {
   /* val client = HttpClient()

    val smsService = SmsServiceImpl()

    val sendVerificationRequest = SendVerificationCodeRequest("09400031542")
    smsService.sendVerification(sendVerificationRequest)

    val validateCodeRequest = VerifyOTPRequest("123456")
    val validateCodeResponse = smsService.validateCode(validateCodeRequest)

    client.close()*/
}