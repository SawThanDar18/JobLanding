package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.util.API_KEY
import co.nexlabs.betterhr.joblanding.util.API_VALUE_JOB
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.Foundation.dataWithContentsOfURL

actual class FileHandler {
    actual suspend fun readFile(uri: String): ByteArray {
        return withContext(Dispatchers.Default) {
            val url = NSURL.fileURLWithPath(uri)
            val data = NSData.dataWithContentsOfURL(url)
            data?.toByteArray() ?: throw IllegalArgumentException("Cannot read file at URI: $uri")
        }
    }
}

fun NSData.toByteArray(): ByteArray {
    val bytes = ByteArray(length.toInt())
    getBytes(bytes)
    return bytes
}
actual fun createHttpClient(): HttpClient {
    return HttpClient(Ios) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        defaultRequest {
            header(API_KEY, API_VALUE_JOB)
        }
    }
}

actual fun createHttpClientWithAuth(bearerToken: String): HttpClient {
    return HttpClient(Ios) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        defaultRequest {
            header("Authorization", "bearer $bearerToken")
        }
    }
}

actual fun createHttpClientWithAuthWithoutToken(): HttpClient {
    return HttpClient(Ios) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
}
