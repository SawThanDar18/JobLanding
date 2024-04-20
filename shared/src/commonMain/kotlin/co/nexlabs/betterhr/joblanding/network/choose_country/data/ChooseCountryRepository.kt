package co.nexlabs.betterhr.joblanding.network.choose_country.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetDynamicPagesRequest

class ChooseCountryRepository(private val jobLandingService: JobLandingService) {

    suspend fun getCountriesList() = jobLandingService.getCountriesList()

    suspend fun getDynamicPages() = jobLandingService.getDynamicPages(GetDynamicPagesRequest("ab18de52-e946-4925-83ab-46f804846034", "mobile"))

    //suspend fun getDynamicPages() = jobLandingService.getDynamicPagesQuery("ab18de52-e946-4925-83ab-46f804846034", "mobile")
}