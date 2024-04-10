package co.nexlabs.betterhr.joblanding.network.api

import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyPhoneNumResponse
import co.nexlabs.betterhr.joblanding.util.API_KEY
import co.nexlabs.betterhr.joblanding.util.API_VALUE
import co.nexlabs.betterhr.joblanding.util.baseUrl
import co.nexlabs.betterhr.joblanding.util.getCountriesUrl
import co.nexlabs.betterhr.joblanding.util.smsUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.http.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class JobLandingServiceImpl(private val client: HttpClient) : JobLandingService {

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

        var response = client.post("${baseUrl}${smsUrl}") {
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

        var response = client.post("${baseUrl}${smsUrl}") {
            headers {
                append(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            setBody(requestBody.toString())
        }

        return response.body()
    }

    override suspend fun getCountriesList(): GetCountriesListResponse {
        val response = client.post("${baseUrl}${getCountriesUrl}") {
            headers {
                append(API_KEY, API_VALUE)
            }
       }
       return response.body()
    }
}