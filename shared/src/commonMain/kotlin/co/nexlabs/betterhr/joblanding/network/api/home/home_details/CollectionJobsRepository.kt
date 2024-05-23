package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class CollectionJobsRepository(private val jobLandingService: JobLandingService) {
    suspend fun getCollectionJobs(collectionId: String, isPaginate: Boolean, limit: Int, page: Int) = jobLandingService.getJobLandingCollectionJobs(collectionId, isPaginate, limit, page)
}