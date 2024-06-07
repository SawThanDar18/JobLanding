package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.util.baseUrlForJob
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import io.ktor.client.HttpClient

expect fun createHttpClient(): HttpClient

expect fun createHttpClientWithAuth(bearerToken: String): HttpClient

expect fun createHttpClientWithAuthWithoutToken(): HttpClient

fun createApolloClient(): ApolloClient {
    val ktorClient = createHttpClient()

    return ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder()
                //.okHttpClient(ktorClient)
                .serverUrl(baseUrlForJob).build()
        )
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}

fun createApolloClientWithAuth(bearerToken: String): ApolloClient {
    val ktorClientWithAuth = createHttpClientWithAuth(bearerToken)

    return ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder()
                //.okHttpClient(ktorClientWithAuth)
                .serverUrl(baseUrlForJob).build()
        )
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}

fun createApolloClientWithAuthWithoutToken(): ApolloClient {
    val ktorClientWithAuthWithoutToken = createHttpClientWithAuthWithoutToken()

    return ApolloClient.Builder()
        .networkTransport(
            HttpNetworkTransport.Builder()
                //.okHttpClient(ktorClientWithAuthWithoutToken)
                .serverUrl(baseUrlForJob).build()
        )
        .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
        .build()
}