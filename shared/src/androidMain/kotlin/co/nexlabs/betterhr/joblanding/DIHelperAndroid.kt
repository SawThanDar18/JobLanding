package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.AssetProvider
import co.nexlabs.betterhr.joblanding.FileHandler
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.register.data.RegisterRepository
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import co.nexlabs.betterhr.joblanding.network.api.JobLandingServiceImpl
import co.nexlabs.betterhr.joblanding.network.api.SharedViewModel
import co.nexlabs.betterhr.joblanding.network.api.application.data.ApplicationRepository
import co.nexlabs.betterhr.joblanding.network.api.application.ApplicationViewModel
import co.nexlabs.betterhr.joblanding.network.api.apply_job.data.ApplyJobRepository
import co.nexlabs.betterhr.joblanding.network.api.apply_job.ApplyJobViewModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.BottomNavigationViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionJobsViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeRepository
import co.nexlabs.betterhr.joblanding.network.api.home.HomeViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionCompaniesRepository
import co.nexlabs.betterhr.joblanding.network.api.home.CollectionCompaniesViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.JobDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionJobsRepository
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailRepository
import co.nexlabs.betterhr.joblanding.network.api.home.CompanyDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailRepository
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxDetailViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.InboxViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitAssignmentViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.SubmitOfferViewModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxRepository
import co.nexlabs.betterhr.joblanding.network.api.interview.InterviewViewModel
import co.nexlabs.betterhr.joblanding.network.api.interview.data.InterviewsRepository
import co.nexlabs.betterhr.joblanding.network.api.login.QRLogInViewModel
import co.nexlabs.betterhr.joblanding.network.api.login.data.QRLogInRepository
import co.nexlabs.betterhr.joblanding.network.api.screen_portal.ScreenPortalViewModel
import co.nexlabs.betterhr.joblanding.network.api.setting.SettingViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryRepository
import co.nexlabs.betterhr.joblanding.network.register.CompleteProfileViewModel
import co.nexlabs.betterhr.joblanding.network.register.ProfileRegisterViewModel
import co.nexlabs.betterhr.joblanding.network.register.data.CompleteProfileRepository
import co.nexlabs.betterhr.joblanding.network.register.data.ProfileRegisterRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.KoinApplication

actual object DIHelperClient {

    private lateinit var koinApp: KoinApplication

    actual fun initialize(localStorage: LocalStorage, fileHandler: FileHandler, assetProvider: AssetProvider) {
        koinApp = startKoin {
            modules(
                module {
                    single {
                        HttpClient() {
                            install(HttpTimeout)
                            install(HttpTimeout) {
                                socketTimeoutMillis = 60_000
                                requestTimeoutMillis = 60_000
                            }
                            install(Logging) {
                                logger = Logger.DEFAULT
                                level = LogLevel.ALL
                                logger = object : Logger {
                                    override fun log(message: String) {
                                        println("msg>>$message")
                                    }
                                }
                            }
                            install(ContentNegotiation) {
                                json(Json {
                                    prettyPrint = true
                                    isLenient = true
                                    ignoreUnknownKeys = true
                                    explicitNulls = false
                                })
                            }
                        }
                    }
                    single<JobLandingService> {
                        JobLandingServiceImpl(
                            localStorage,
                            fileHandler,
                            get()
                        )
                    }
                    single { RegisterRepository(get()) }
                    single { ChooseCountryRepository(get()) }
                    single { HomeRepository(get()) }
                    single { CollectionJobsRepository(get()) }
                    single { CollectionCompaniesRepository(get()) }
                    single { JobDetailRepository(get()) }
                    single { CompanyDetailRepository(get()) }
                    single { ProfileRegisterRepository(get()) }
                    single { ApplyJobRepository(get()) }
                    single { CompleteProfileRepository(get()) }
                    single { ApplicationRepository(get()) }
                    single { InboxRepository(get()) }
                    single { InterviewsRepository(get()) }
                    single { QRLogInRepository(get()) }
                    factory { ScreenPortalViewModel(localStorage) }
                    factory { RegisterViewModel(localStorage, get()) }
                    factory { ChooseCountryViewModel(localStorage, get()) }
                    factory { HomeViewModel(localStorage, get()) }
                    factory { SharedViewModel() }
                    factory { CollectionJobsViewModel(get()) }
                    factory { CollectionCompaniesViewModel(get()) }
                    factory { JobDetailViewModel(localStorage, get()) }
                    factory { CompanyDetailViewModel(get()) }
                    factory { BottomNavigationViewModel(localStorage) }
                    factory { ProfileRegisterViewModel(localStorage, get()) }
                    factory { ApplyJobViewModel(localStorage, get()) }
                    factory { CompleteProfileViewModel(localStorage, get()) }
                    factory { ApplicationViewModel(localStorage, get()) }
                    factory { InboxViewModel(localStorage, get()) }
                    factory { InboxDetailViewModel(get(), assetProvider) }
                    factory { SubmitAssignmentViewModel(localStorage, get()) }
                    factory { SubmitOfferViewModel(localStorage, get()) }
                    factory { InterviewViewModel(localStorage, get()) }
                    factory { QRLogInViewModel(localStorage, get()) }
                    factory { SettingViewModel(localStorage) }
                }
            )
        }
    }
    
    actual fun getScreenPortalViewModel(): ScreenPortalViewModel {
        return koinApp.koin.get()
    }    
    
    actual fun getRegisterViewModel(): RegisterViewModel {
        return koinApp.koin.get()
    }

    actual fun getChooseCountryViewModel(): ChooseCountryViewModel {
        return koinApp.koin.get()
    }

    actual fun getHomeViewModel(): HomeViewModel {
        return koinApp.koin.get()
    }

    actual fun getSharedViewModel(): SharedViewModel {
        return koinApp.koin.get()
    }

    actual fun getCollectionJobsViewModel(): CollectionJobsViewModel {
        return koinApp.koin.get()
    }

    actual fun getCollectionCompaniesViewModel(): CollectionCompaniesViewModel {
        return koinApp.koin.get()
    }

    actual fun getJobDetailViewModel(): JobDetailViewModel {
        return koinApp.koin.get()
    }

    actual fun getCompanyDetailViewModel(): CompanyDetailViewModel {
        return koinApp.koin.get()
    }

    actual fun getBottomNavigationViewModel(): BottomNavigationViewModel {
        return koinApp.koin.get()
    }

    actual fun getProfileRegisterViewModel(): ProfileRegisterViewModel {
        return koinApp.koin.get()
    }

    actual fun getApplyJobViewModel(): ApplyJobViewModel {
        return koinApp.koin.get()
    }

    actual fun getCompleteProfileViewModel(): CompleteProfileViewModel {
        return koinApp.koin.get()
    }

    actual fun getApplicationViewModel(): ApplicationViewModel {
        return koinApp.koin.get()
    }

    actual fun getInboxViewModel(): InboxViewModel {
        return koinApp.koin.get()
    }

    actual fun getInboxDetailViewModel(): InboxDetailViewModel {
        return koinApp.koin.get()
    }

    actual fun getSubmitAssignmentViewModel(): SubmitAssignmentViewModel {
        return koinApp.koin.get()
    }

    actual fun getSubmitOfferViewModel(): SubmitOfferViewModel {
        return koinApp.koin.get()
    }

    actual fun getInterviewViewModel(): InterviewViewModel {
        return koinApp.koin.get()
    }

    actual fun getQRLogInViewModel(): QRLogInViewModel {
        return koinApp.koin.get()
    }

    actual fun getSettingViewModel(): SettingViewModel {
        return koinApp.koin.get()
    }

}