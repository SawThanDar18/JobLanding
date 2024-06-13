package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class CollectionCompaniesRepository(private val jobLandingService: JobLandingService) {
    suspend fun getCollectionCompanies(collectionId: String, isPaginate: Boolean, limit: Int, page: Int) = jobLandingService.getJobLandingCollectionCompanies(collectionId, isPaginate, limit, page)
}