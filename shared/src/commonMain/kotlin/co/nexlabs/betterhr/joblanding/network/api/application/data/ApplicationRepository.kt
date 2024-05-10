package co.nexlabs.betterhr.joblanding.network.api.application.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class ApplicationRepository(private val jobLandingService: JobLandingService) {
    suspend fun fetchApplication(
        limit: Int
    ) = jobLandingService.fetchApplication(limit)

    suspend fun getJobLandingJobList(jobIds: List<String>) = jobLandingService.getJobLandingJobList(jobIds)

    suspend fun fetchApplicationById(
        id: String
    ) = jobLandingService.fetchApplicationById(id)
}