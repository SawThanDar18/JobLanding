package co.nexlabs.betterhr.joblanding.di

import co.nexlabs.betterhr.joblanding.network.register.RegisterRepository
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import co.nexlabs.betterhr.joblanding.network.api.JobLandingServiceImpl
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

fun initKoin() {
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
                                    println("message>>$message")
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
                factory { RegisterViewModel(get()) }
                factory { ChooseCountryViewModel(get()) }
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