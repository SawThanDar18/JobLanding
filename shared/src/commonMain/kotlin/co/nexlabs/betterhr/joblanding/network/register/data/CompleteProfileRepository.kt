package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class CompleteProfileRepository(private val jobLandingService: JobLandingService) {
    suspend fun getCandidateData() = jobLandingService.getCandidateDatas()
}