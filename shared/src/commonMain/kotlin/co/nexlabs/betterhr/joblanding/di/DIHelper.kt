package co.nexlabs.betterhr.joblanding.di

import android.app.Application
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
import co.nexlabs.betterhr.joblanding.network.api.screen_portal.ScreenPortalViewModel
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

fun initKoin(application: Application) {
    startKoin {
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
                            logger = object: Logger {
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
                single<JobLandingService> {JobLandingServiceImpl(application, get())}
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
                factory { ScreenPortalViewModel(application) }
                factory { RegisterViewModel(application, get()) }
                factory { ChooseCountryViewModel(application, get()) }
                factory { HomeViewModel(application, get()) }
                factory { SharedViewModel() }
                factory { CollectionJobsViewModel(get()) }
                factory { CollectionCompaniesViewModel(get()) }
                factory { JobDetailViewModel(application, get()) }
                factory { CompanyDetailViewModel(get()) }
                factory { BottomNavigationViewModel(application) }
                factory { ProfileRegisterViewModel(application, get()) }
                factory { ApplyJobViewModel(application, get()) }
                factory { CompleteProfileViewModel(application, get()) }
                factory { ApplicationViewModel(application, get()) }
                factory { InboxViewModel(application, get()) }
                factory { InboxDetailViewModel(application, get()) }
                factory { SubmitAssignmentViewModel(application, get()) }
                factory { SubmitOfferViewModel(application, get()) }
                factory { InterviewViewModel(get()) }
            }
        )
    }
}




/*single {
                   ApolloClient.Builder()
                       .serverUrl("https://countries.trevorblades.com/graphql")
                       .normalizedCache(MemoryCacheFactory(maxSizeBytes = 10 * 1024 * 1024))
                       .build()
               }
               single<CountriesAPI> { CountriesAPIImpl(get()) }
               single { ContinentsRepository(get()) }
               single { CountriesRepository(get()) }
               single { CountryRepository(get()) }
               factory { ContinentsViewModel(get()) }
               factory { CountriesViewModel(get()) }
               factory { CountryViewModel(get()) }*/