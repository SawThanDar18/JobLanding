package co.nexlabs.betterhr.joblanding.screen.country.data

import co.nexlabs.betterhr.joblanding.network.CountriesAPI


class CountryRepository(private val countriesAPI: CountriesAPI) {

    suspend fun countryQuery(code: String) = countriesAPI.countryQuery(code)
}