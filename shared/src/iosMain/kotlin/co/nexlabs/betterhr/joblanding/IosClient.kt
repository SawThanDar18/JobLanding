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
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import platform.darwin.dispatch_async
import kotlinx.coroutines.CoroutineDispatcher
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_queue_t
import platform.darwin.DISPATCH_QUEUE_PRIORITY_DEFAULT
import kotlinx.coroutines.Runnable
import kotlin.coroutines.CoroutineContext
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.stringWithContentsOfFile
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSURL

class IosFileUri(private val iosUrl: NSURL) : FileUri {
    override val uri: String
        get() = iosUrl.absoluteString ?: ""
}

class IosAssetProvider : AssetProvider {
    @OptIn(ExperimentalForeignApi::class)
    override fun getAssetContent(fileName: String): String {
        val path = NSBundle.mainBundle.pathForResource(fileName, ofType = null) ?: return ""
        val content = NSString.stringWithContentsOfFile(path, NSUTF8StringEncoding, null) ?: return ""
        return content as String
    }
}

actual object DispatcherProvider {
    actual val io: CoroutineDispatcher = NsQueueDispatcher(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT.toLong(), 0u))
}

private class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
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
