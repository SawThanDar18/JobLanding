package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.util.baseUrlForAuth
import co.nexlabs.betterhr.joblanding.util.baseUrlForJob
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import io.ktor.client.HttpClient

expect class FileHandler {
    suspend fun readFile(uri: String): ByteArray
}

expect fun createHttpClient(): HttpClient

expect fun createHttpClientWithAuth(bearerToken: String): HttpClient

expect fun createHttpClientWithAuthWithoutToken(): HttpClient

fun createApolloClient(): ApolloClient {
    val ktorClient = createHttpClient()

    return ApolloClient.Builder()
        .serverUrl(baseUrlForJob)
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}

fun createApolloClientWithAuth(bearerToken: String): ApolloClient {
    val ktorClientWithAuth = createHttpClientWithAuth(bearerToken)

    return ApolloClient.Builder()
        .serverUrl(baseUrlForAuth)
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}

fun createApolloClientWithAuthWithoutToken(): ApolloClient {
    val ktorClientWithAuthWithoutToken = createHttpClientWithAuthWithoutToken()

    return ApolloClient.Builder()
        .serverUrl(baseUrlForAuth)
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}