package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.util.API_KEY
import co.nexlabs.betterhr.joblanding.util.API_VALUE_JOB
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header

import android.content.Context
import android.net.Uri
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import java.io.FileInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

actual class FileHandler(private val context: Context) {
    actual suspend fun readFile(uri: String): ByteArray {
        return withContext(Dispatchers.IO) {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(Uri.parse(uri), "r")
                ?: throw IllegalArgumentException("Cannot open file descriptor for URI: $uri")

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            inputStream.readBytes().also {
                inputStream.close()
                parcelFileDescriptor.close()
            }
        }
    }
}

actual fun createHttpClient(): HttpClient {
    return HttpClient(OkHttp) {
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
    return HttpClient(OkHttp) {
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
    return HttpClient(OkHttp) {
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

