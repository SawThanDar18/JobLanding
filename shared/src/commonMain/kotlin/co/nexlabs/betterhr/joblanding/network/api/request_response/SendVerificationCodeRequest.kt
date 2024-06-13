package co.nexlabs.betterhr.joblanding.network.api.request_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendVerificationCodeRequest(val phone: String) {
    @SerialName("query")
    val query = "mutation(\$phone:String!) {\n" +
            "  sendVerificationCode (phone: \$phone) {\n" +
            "    status\n" +
            "    message\n" +
            "    token\n" +
            "  }\n" +
            "}"

    @SerialName("variables")
    val variables = mapOf("phone" to phone)
}

@Serializable
data class SendVerificationResponse(@SerialName("data") val data: VerifyResponse) {
    @Serializable
    data class VerifyResponse(@SerialName("sendVerificationCode") val response: Response)

    @Serializable
    data class Response(
        @SerialName("status") val status: Int,
        @SerialName("message") val message: String,
        @SerialName("token") val token: String?
    )
}
