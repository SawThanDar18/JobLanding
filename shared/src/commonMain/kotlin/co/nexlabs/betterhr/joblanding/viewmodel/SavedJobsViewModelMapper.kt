package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.without_auth.JobLandingSavedJobsQuery
import co.nexlabs.betterhr.joblanding.network.api.setting.data.SaveJobsUIModel
import co.nexlabs.betterhr.joblanding.network.api.setting.data.SavedJobsCompanyUIModel

object SavedJobsViewModelMapper {

    fun mapDataToViewModel(data: JobLandingSavedJobsQuery.Data): List<SaveJobsUIModel> {
        return data.jobLandingJobList.map {
            SaveJobsUIModel(
                it.id,
                SavedJobsCompanyUIModel(
                    it.company!!.id,
                    it.company.name ?: "",
                    it.company.company_logo ?: ""
                ),
                it.title ?: "",
                it.created_by ?: "",
                it.state_name ?: "",
                it.hiring_date ?: "",
                it.min_salary ?: 0.0,
                it.max_salary ?: 0.0,
                it.currency_code ?: ""
            )
        }
    }
}