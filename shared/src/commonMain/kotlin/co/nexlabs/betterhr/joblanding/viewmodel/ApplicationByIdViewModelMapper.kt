package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.FetchApplicationByIdQuery
import co.nexlabs.betterhr.joblanding.network.api.application.data.ApplicationByIdUIModel
import co.nexlabs.betterhr.joblanding.network.api.application.data.ApplicationHistoryUIModel

object ApplicationByIdViewModelMapper {

    fun mapDataToViewModel(data: FetchApplicationByIdQuery.Application): ApplicationByIdUIModel {
        var applicationHistoriesList: MutableList<ApplicationHistoryUIModel> = ArrayList()
        if (data.applicationHistories != null) {
            data.applicationHistories.map {
                it?.let {
                    applicationHistoriesList.add(
                        ApplicationHistoryUIModel(
                            it.id, it.application_status ?: "", it.application_date ?: "", it.have_assignment ?: false, it.assignment_submitted ?: false
                        )
                    )
                }
            }
        }

        return ApplicationByIdUIModel(
            data.id,
            data.subdomain ?: "",
            data.reference_job_id ?: "",
            data.job_title ?: "",
            data.status ?: "",
            data.applied_date ?: "",
            data.current_job_title ?: "",
            data.current_company ?: "",
            data.working_since ?: "",
            applicationHistoriesList
        )
    }
}