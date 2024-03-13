package co.nexlabs.betterhr.joblanding.network

import co.nexlabs.betterhr.joblanding.ContinentQuery
import co.nexlabs.betterhr.joblanding.ContinentsQuery
import co.nexlabs.betterhr.joblanding.CountryQuery
import com.apollographql.apollo3.ApolloCall
import com.apollographql.apollo3.ApolloClient
import co.nexlabs.betterhr.joblanding.network.CountriesAPI

class CountriesAPIImpl(private val apolloClient: ApolloClient) : CountriesAPI {
    override suspend fun continentsQuery(): ApolloCall<ContinentsQuery.Data> {
        return apolloClient.query(ContinentsQuery())
    }

    override suspend fun continentQuery(id: String): ApolloCall<ContinentQuery.Data> {
        return apolloClient.query(ContinentQuery(id))
    }

    override suspend fun countryQuery(code: String): ApolloCall<CountryQuery.Data> {
        return apolloClient.query(CountryQuery(code))
    }
}