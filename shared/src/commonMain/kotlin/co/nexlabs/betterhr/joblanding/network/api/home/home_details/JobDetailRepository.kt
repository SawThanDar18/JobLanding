package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class JobDetailRepository(private val jobLandingService: JobLandingService) {
    suspend fun getJobDetail(jobId: String) = jobLandingService.getJobDetail(jobId)
}