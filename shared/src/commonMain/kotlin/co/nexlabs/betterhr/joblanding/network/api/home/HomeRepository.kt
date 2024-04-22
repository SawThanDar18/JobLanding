package co.nexlabs.betterhr.joblanding.network.api.home

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class HomeRepository(private val jobLandingService: JobLandingService) {
    suspend fun getDynamicPages() = jobLandingService.getDynamicPagesQuery("ab18de52-e946-4925-83ab-46f804846034", "mobile")
}