package co.nexlabs.betterhr.joblanding.network.choose_country.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class ChooseCountryRepository(private val jobLandingService: JobLandingService) {

    suspend fun getCountriesList() = jobLandingService.getCountriesList()
    suspend fun getDynamicPages() = jobLandingService.getDynamicPagesQuery("ab18de52-e946-4925-83ab-46f804846034", "website")
}