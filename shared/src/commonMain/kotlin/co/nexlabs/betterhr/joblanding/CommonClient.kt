package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.util.API_KEY
import co.nexlabs.betterhr.joblanding.util.API_VALUE_JOB
import co.nexlabs.betterhr.joblanding.util.baseUrlForAuth
import co.nexlabs.betterhr.joblanding.util.baseUrlForJob
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.http.HttpHeader
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineDispatcher

interface FileHandler {
    fun readFileBytes(fileUri: FileUri): ByteArray
}

interface FileUri {
    val uri: String
}

interface AssetProvider {
    fun getAssetContent(fileName: String): String
}

expect object DispatcherProvider {
    val io: CoroutineDispatcher
}

expect fun createHttpClientNonAuth(): HttpClient

/*expect fun createHttpClient(): HttpClient

expect fun createHttpClientWithAuth(bearerToken: String): HttpClient

expect fun createHttpClientWithAuthWithoutToken(): HttpClient*/

fun createApolloClient(): ApolloClient {
    val headers = mapOf(
        API_KEY to API_VALUE_JOB,
    )

    val httpHeaders = headers.map { (key, value) ->
        HttpHeader(key, value)
    }

    return ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder()
                .httpHeaders(httpHeaders)
                .serverUrl(baseUrlForJob)
                .build()
        )
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}

fun createApolloClientWithAuth(bearerToken: String): ApolloClient {
    val headers = mapOf(
        "Authorization" to "bearer $bearerToken",
    )

    val httpHeaders = headers.map { (key, value) ->
        HttpHeader(key, value)
    }

    return ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder()
                .httpHeaders(httpHeaders)
                .serverUrl(baseUrlForAuth)
                .build()
        )
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}

fun createApolloClientWithAuthWithoutToken(): ApolloClient {

    return ApolloClient.Builder()
        .serverUrl(baseUrlForAuth)
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}