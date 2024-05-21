package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class CompanyDetailRepository(private val jobLandingService: JobLandingService) {
    suspend fun getCompanyDetail(companyId: String) = jobLandingService.getCompanyDetail(companyId)
    suspend fun getCompanyDetailJob(companyId: String) = jobLandingService.getCompanyDetailJob(companyId)


}