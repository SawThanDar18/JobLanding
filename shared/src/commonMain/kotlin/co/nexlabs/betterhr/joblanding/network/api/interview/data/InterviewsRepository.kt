package co.nexlabs.betterhr.joblanding.network.api.interview.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class InterviewsRepository(private val jobLandingService: JobLandingService) {
    suspend fun fetchInterviews(limit: Int, page: Int) = jobLandingService.fetchInterview(limit, page)
}