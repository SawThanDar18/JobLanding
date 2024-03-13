package co.nexlabs.betterhr.joblanding.screen.continents.data

import co.nexlabs.betterhr.joblanding.network.CountriesAPI

class ContinentsRepository(private val countriesAPI: CountriesAPI) {
    suspend fun continentsQuery() = countriesAPI.continentsQuery()
}