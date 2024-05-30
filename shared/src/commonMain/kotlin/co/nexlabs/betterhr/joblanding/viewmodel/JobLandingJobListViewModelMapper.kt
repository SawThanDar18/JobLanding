package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.without_auth.JobLandingJobListQuery
import co.nexlabs.betterhr.joblanding.network.api.application.data.CompanyInfoUIModel
import co.nexlabs.betterhr.joblanding.network.api.application.data.JobLandingJobListUIModel

object JobLandingJobListViewModelMapper {
    fun mapResponseToViewModel(response: JobLandingJobListQuery.Data): List<JobLandingJobListUIModel> {
        return response.jobLandingJobList.map {
            mapDataToCompanyInfo(it)
        }
    }

    fun mapDataToCompanyInfo(data: JobLandingJobListQuery.JobLandingJobList): JobLandingJobListUIModel {
        return JobLandingJobListUIModel(
            data.id,
            CompanyInfoUIModel(
                data.company!!.id, data.company.name, data.company.company_logo ?: ""
            )
        )
    }
}