package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class JobDetailRepository(private val jobLandingService: JobLandingService) {
    suspend fun getJobDetail(jobId: String) = jobLandingService.getJobDetail(jobId)

    suspend fun saveJob(candidateId: String, jobId: String) = jobLandingService.saveJob(candidateId, jobId)

    suspend fun fetchSaveJobsById(jobId: String) = jobLandingService.fetchSaveJobsById(jobId)

    suspend fun unSaveJob(id: String) = jobLandingService.unSaveJob(id)
}