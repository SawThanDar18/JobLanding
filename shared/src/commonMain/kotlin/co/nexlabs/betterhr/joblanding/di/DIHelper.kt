package co.nexlabs.betterhr.joblanding.di

import android.app.Application
import co.nexlabs.betterhr.joblanding.network.register.RegisterRepository
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import co.nexlabs.betterhr.joblanding.network.api.JobLandingServiceImpl
import co.nexlabs.betterhr.joblanding.network.api.SharedViewModel
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
import co.nexlabs.betterhr.joblanding.network.api.screen_portal.ScreenPortalViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryRepository
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
                single<JobLandingService> {JobLandingServiceImpl(get())}
                single { RegisterRepository(get()) }
                single { ChooseCountryRepository(get()) }
                single { HomeRepository(get()) }
                single { CollectionJobsRepository(get()) }
                single { CollectionCompaniesRepository(get()) }
                single { JobDetailRepository(get()) }
                single { CompanyDetailRepository(get()) }
                factory { ScreenPortalViewModel(application) }
                factory { RegisterViewModel(application, get()) }
                factory { ChooseCountryViewModel(application, get()) }
                factory { HomeViewModel(application, get()) }
                factory { SharedViewModel() }
                factory { CollectionJobsViewModel(get()) }
                factory { CollectionCompaniesViewModel(get()) }
                factory { JobDetailViewModel(get()) }
                factory { CompanyDetailViewModel(get()) }
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