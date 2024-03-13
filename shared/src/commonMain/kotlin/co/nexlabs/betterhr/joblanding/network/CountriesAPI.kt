package co.nexlabs.betterhr.joblanding.network

import co.nexlabs.betterhr.joblanding.ContinentQuery
import co.nexlabs.betterhr.joblanding.ContinentsQuery
import co.nexlabs.betterhr.joblanding.CountryQuery
import com.apollographql.apollo3.ApolloCall

interface CountriesAPI {

    suspend fun continentsQuery(): ApolloCall<ContinentsQuery.Data>

    suspend fun continentQuery(id: String): ApolloCall<ContinentQuery.Data>

    suspend fun countryQuery(code: String): ApolloCall<CountryQuery.Data>
}