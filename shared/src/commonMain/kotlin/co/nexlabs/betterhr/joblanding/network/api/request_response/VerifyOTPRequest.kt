package co.nexlabs.betterhr.joblanding.network.api.request_response

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
data class VerifyPhoneNumResponse(val data: VerifyResponse) {

    @Serializable
    data class VerifyResponse(val verifyPhoneNumber: Response)

    @Serializable
    data class Response(
        val status: Int,
        val message: String,
        val token: String?
    )
}
