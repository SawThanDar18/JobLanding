package co.nexlabs.betterhr.joblanding.network.choose_country.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class ChooseCountryRepository(private val jobLandingService: JobLandingService) {

    suspend fun getCountriesList() = jobLandingService.getCountriesList()
}