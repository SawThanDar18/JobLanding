package co.nexlabs.betterhr.joblanding.network.api.setting.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class SavedJobsRepository(private val jobLandingService: JobLandingService) {
    suspend fun fetchSavedJobsIds() = jobLandingService.getSavedJobsIds()
    suspend fun fetchSavedJobs(jobIds: List<String>) = jobLandingService.getSavedJobs(jobIds)
}