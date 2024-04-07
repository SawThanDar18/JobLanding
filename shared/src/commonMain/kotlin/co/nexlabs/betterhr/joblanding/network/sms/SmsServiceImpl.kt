package co.nexlabs.betterhr.joblanding.network.sms

import co.nexlabs.betterhr.joblanding.util.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.*
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class SmsServiceImpl(private val client: HttpClient) : SmsService {

    override suspend fun sendVerification(body: SendVerificationCodeRequest): SendVerificationResponse {

        val mutation = """
        mutation SendVerificationCode(${'$'}phone: String!) {
            sendVerificationCode(phone: ${'$'}phone) {
                status
                message
                token
            }
        }
    """.trimIndent()

        val variables = mapOf("phone" to body.phone)

        val requestBody = buildJsonObject {
            put("query", mutation)
            put("variables", buildJsonObject {
                variables.forEach { (key, value) ->
                    put(key, JsonPrimitive(value))
                }
            })
        }

        var response = client.post(baseUrl) {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(requestBody.toString())
        }

        return response.body()
    }

    override suspend fun validateCode(body: VerifyOTPRequest): VerifyPhoneNumResponse {

        val mutation = """
        mutation VerifyPhoneNumber(${'$'}code: String!) {
            verifyPhoneNumber(code: ${'$'}code) {
                status
                message
                token
            }
        }
    """.trimIndent()

        val variables = mapOf("code" to body.code)

        val requestBody = buildJsonObject {
            put("query", mutation)
            put("variables", buildJsonObject {
                variables.forEach { (key, value) ->
                    put(key, JsonPrimitive(value))
                }
            })
        }

        var response = client.post(baseUrl) {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(requestBody.toString())
        }

        return response.body()
    }
}