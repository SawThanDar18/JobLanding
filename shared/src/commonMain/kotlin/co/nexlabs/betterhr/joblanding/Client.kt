package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.util.baseUrlForAuth
import co.nexlabs.betterhr.joblanding.util.baseUrlForJob
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.http.HttpNetworkTransport
import io.ktor.client.HttpClient
expect fun createHttpClient(): HttpClient

expect fun createHttpClientWithAuth(bearerToken: String): HttpClient

expect fun createHttpClientWithAuthWithoutToken(): HttpClient

fun createApolloClient(): ApolloClient {
    val ktorClient = createHttpClient()

    return ApolloClient.Builder()
        /*.networkTransport(
            HttpNetworkTransport(
                client = ktorClient,
                serverUrl = baseUrlForJob
            )
        )*/
        .build()
}

fun createApolloClientWithAuth(bearerToken: String): ApolloClient {
    val ktorClientWithAuth = createHttpClientWithAuth(bearerToken)

    return ApolloClient.Builder()
        /*.networkTransport(
            HttpNetworkTransport(
                client = ktorClientWithAuth,
                serverUrl = baseUrlForAuth
            )
        )*/
        .build()
}

fun createApolloClientWithAuthWithoutToken(): ApolloClient {
    val ktorClient = createHttpClientWithAuthWithoutToken()

    return ApolloClient.Builder()
        /*.networkTransport(
            HttpNetworkTransport(
                client = ktorClient,
                serverUrl = baseUrlForAuth
            )
        )*/
        .build()
}