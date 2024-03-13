package co.nexlabs.betterhr.joblanding.screen.countries.data

import co.nexlabs.betterhr.joblanding.network.CountriesAPI


class CountriesRepository(private val countriesAPI: CountriesAPI) {

    suspend fun continentQuery(id: String) = countriesAPI.continentQuery(id)
}