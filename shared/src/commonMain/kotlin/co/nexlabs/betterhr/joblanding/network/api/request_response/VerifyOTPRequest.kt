package co.nexlabs.betterhr.joblanding.network.api.request_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyOTPRequest(val code: String) {
    val query = "mutation(\$code:String!){\n" +
            "  verifyPhoneNumber(code:\$code){\n" +
            "    status\n" +
            "    message\n" +
            "    token\n" +
            "  }\n" +
            "}"
    val variables = mapOf("code" to code)
}

@Serializable
data class VerifyPhoneNumResponse(@SerialName("data") val data: VerifyResponse) {

    @Serializable
    data class VerifyResponse(@SerialName("verifyPhoneNumber") val verifyPhoneNumber: Response)

    @Serializable
    data class Response(
        @SerialName("status") val status: Int,
        @SerialName("message") val message: String,
        @SerialName("token") val token: String?
    )
}
