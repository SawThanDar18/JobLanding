package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.SharedViewModel
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationFilterViewModel
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationViewModel
import co.nexlabs.betterhr.joblanding.network.api.apply_job.ApplyJobViewModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.BottomNavigationViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionCompaniesViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionJobsViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CompanyDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.HomeViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.JobDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitAssignmentViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitOfferViewModel
import co.nexlabs.betterhr.joblanding.network.api.interview.InterviewViewModel
import co.nexlabs.betterhr.joblanding.network.api.login.QRLogInViewModel
import co.nexlabs.betterhr.joblanding.network.api.screen_portal.ScreenPortalViewModel
import co.nexlabs.betterhr.joblanding.network.api.setting.SettingViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.register.CompleteProfileViewModel
import co.nexlabs.betterhr.joblanding.network.register.ProfileRegisterViewModel
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
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

expect object DIHelperClient {
    fun initialize(localStorage: LocalStorage, fileHandler: FileHandler, assetProvider: AssetProvider)
    fun getScreenPortalViewModel(): ScreenPortalViewModel
    fun getRegisterViewModel(): RegisterViewModel
    fun getChooseCountryViewModel(): ChooseCountryViewModel
    fun getHomeViewModel(): HomeViewModel
    fun getSharedViewModel(): SharedViewModel
    fun getCollectionJobsViewModel(): CollectionJobsViewModel
    fun getCollectionCompaniesViewModel(): CollectionCompaniesViewModel
    fun getJobDetailViewModel(): JobDetailViewModel
    fun getCompanyDetailViewModel(): CompanyDetailViewModel
    fun getBottomNavigationViewModel(): BottomNavigationViewModel
    fun getProfileRegisterViewModel(): ProfileRegisterViewModel
    fun getApplyJobViewModel(): ApplyJobViewModel
    fun getCompleteProfileViewModel(): CompleteProfileViewModel
    fun getApplicationViewModel(): ApplicationViewModel
    fun getInboxViewModel(): InboxViewModel
    fun getInboxDetailViewModel(): InboxDetailViewModel
    fun getSubmitAssignmentViewModel(): SubmitAssignmentViewModel
    fun getSubmitOfferViewModel(): SubmitOfferViewModel
    fun getInterviewViewModel(): InterviewViewModel
    fun getQRLogInViewModel(): QRLogInViewModel
    fun getSettingViewModel(): SettingViewModel
    fun getApplicationFilterViewModel(): ApplicationFilterViewModel
}

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
    print("be>>$bearerToken")
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

