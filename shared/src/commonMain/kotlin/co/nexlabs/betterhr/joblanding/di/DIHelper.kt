package co.nexlabs.betterhr.joblanding.di

import co.nexlabs.betterhr.joblanding.network.CountriesAPI
import co.nexlabs.betterhr.joblanding.network.CountriesAPIImpl
import co.nexlabs.betterhr.joblanding.screen.continents.ContinentsViewModel
import co.nexlabs.betterhr.joblanding.screen.continents.data.ContinentsRepository
import co.nexlabs.betterhr.joblanding.screen.countries.CountriesViewModel
import co.nexlabs.betterhr.joblanding.screen.countries.data.CountriesRepository
import co.nexlabs.betterhr.joblanding.screen.country.CountryViewModel
import co.nexlabs.betterhr.joblanding.screen.country.data.CountryRepository
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.cache.normalized.api.MemoryCacheFactory
import com.apollographql.apollo3.cache.normalized.normalizedCache
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(
            module {
                single {
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
                factory { CountryViewModel(get()) }
            }
        )
    }
}