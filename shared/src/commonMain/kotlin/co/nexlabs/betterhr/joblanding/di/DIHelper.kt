package co.nexlabs.betterhr.joblanding.di

import co.nexlabs.betterhr.joblanding.network.CountriesAPI
import co.nexlabs.betterhr.joblanding.network.CountriesAPIImpl
import co.nexlabs.betterhr.joblanding.network.register.RegisterRepository
import co.nexlabs.betterhr.joblanding.network.register.RegisterViewModel
import co.nexlabs.betterhr.joblanding.network.sms.SmsService
import co.nexlabs.betterhr.joblanding.network.sms.SmsServiceImpl
import co.nexlabs.betterhr.joblanding.screen.continents.ContinentsViewModel
import co.nexlabs.betterhr.joblanding.screen.continents.data.ContinentsRepository
import co.nexlabs.betterhr.joblanding.screen.countries.CountriesViewModel
import co.nexlabs.betterhr.joblanding.screen.countries.data.CountriesRepository
import co.nexlabs.betterhr.joblanding.screen.country.CountryViewModel
import co.nexlabs.betterhr.joblanding.screen.country.data.CountryRepository
import co.nexlabs.betterhr.joblanding.util.baseUrl
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.*
import kotlinx.serialization.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json

fun initKoin() {
    startKoin {
        modules(
            module {
                single {
                    HttpClient() {
                        /*install(JsonFeature) {
                            serializer = KotlinxSerializer()
                        }*/
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
                        /*defaultRequest {
                            header("Content-Type", "application/json")
                            url(baseUrl)
                        }*/
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
                single<SmsService> {SmsServiceImpl(get())}
                single { RegisterRepository(get()) }
                factory { RegisterViewModel(get()) }

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
            }
        )
    }
}