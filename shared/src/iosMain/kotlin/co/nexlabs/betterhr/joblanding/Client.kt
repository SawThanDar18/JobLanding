package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.util.API_KEY
import co.nexlabs.betterhr.joblanding.util.API_VALUE_JOB
import io.ktor.client.HttpClient
import io.ktor.client.engine.ios.Ios
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header

actual fun createHttpClient(): HttpClient {
    return HttpClient(Ios) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.HEADERS
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
    }
}
