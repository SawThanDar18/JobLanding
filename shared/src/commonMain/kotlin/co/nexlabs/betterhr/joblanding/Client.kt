package co.nexlabs.betterhr.joblanding

import io.ktor.client.HttpClient

expect val Client: HttpClient

/*
@OptIn(InternalAPI::class)
suspend fun sendVerification(
    client: HttpClient,
    baseUrl: String,
    body: SendVerificationCodeRequest
): SendVerificationResponse {
    val response = client.post("$baseUrl/graphql") {
        headers {
            append("Content-Type", "application/json")
        }
        this.body = Json.encodeToString(body)
    }
    return Json.decodeFromString(response.bodyAsText())
}

@OptIn(InternalAPI::class)
suspend fun validateCode(
    client: HttpClient,
    baseUrl: String,
    body: VerifyOTPRequest
): VerifyPhoneNumResponse {
    val response = client.post("$baseUrl/graphql") {
        headers {
            append("Content-Type", "application/json")
        }
        this.body = Json.encodeToString(body)
    }

    return Json.decodeFromString(response.bodyAsText())
}*/
