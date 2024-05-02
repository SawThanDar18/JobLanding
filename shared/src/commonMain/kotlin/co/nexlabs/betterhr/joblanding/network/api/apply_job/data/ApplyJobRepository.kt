package co.nexlabs.betterhr.joblanding.network.api.apply_job.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class ApplyJobRepository(private val jobLandingService: JobLandingService) {
    suspend fun applyJob(referenceId: String, candidateId: String, jobId: String, status: String, subDomain: String) =
        jobLandingService.applyJob(referenceId, candidateId, jobId, status, subDomain)
}