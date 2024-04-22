package co.nexlabs.betterhr.joblanding.network.api.home.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class HomeRepository(private val jobLandingService: JobLandingService) {
    suspend fun getJobLandingSections(pageId: String) = jobLandingService.getJobLandingSections(pageId)
}