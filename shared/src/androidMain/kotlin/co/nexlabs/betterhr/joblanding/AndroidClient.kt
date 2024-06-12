package co.nexlabs.betterhr.joblanding

import android.content.ContentResolver
import android.content.Context
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
import android.net.Uri
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.util.API_VALUE
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import java.io.FileInputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import kotlinx.coroutines.CoroutineDispatcher
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
class AndroidFileHandler(private val contentResolver: ContentResolver) : FileHandler {
    override fun readFileBytes(fileUri: FileUri): ByteArray {
        val uri = fileUri.toAndroidUri()
        val parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r", null)!!
        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        return inputStream.readBytes()
    }

    private fun FileUri.toAndroidUri(): Uri {
        return Uri.parse(this.uri)
    }
}

class AndroidFileUri(private val androidUri: Uri) : FileUri {
    override val uri: String
        get() = androidUri.toString()
}

class AndroidAssetProvider(private val context: Context) : AssetProvider {
    override fun getAssetContent(fileName: String): String {
        val reader = BufferedReader(
            InputStreamReader(context.assets.open(fileName), StandardCharsets.UTF_8)
        )
        val content = reader.use { it.readText() }
        return content
    }
}

actual object DispatcherProvider {
    actual val io: CoroutineDispatcher = Dispatchers.IO
}

actual fun createHttpClientNonAuth() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            isLenient = true
        })
    }
    install(Logging) {
        level = LogLevel.INFO
    }
    defaultRequest {
        header(API_KEY, API_VALUE)
    }
}

/*actual fun createHttpClient(): HttpClient {
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
}*/

