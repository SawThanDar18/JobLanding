package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.FetchApplicationQuery
import co.nexlabs.betterhr.joblanding.network.api.application.data.ApplicationUIModel

object ApplicationViewModelMapper {

    fun mapResponseToViewModel(response: FetchApplicationQuery.Data): List<ApplicationUIModel> {
        return response.applications.map {
            mapDataToViewModel(it)
        }
    }

    fun mapDataToViewModel(data: FetchApplicationQuery.Application): ApplicationUIModel {

        return ApplicationUIModel(
            data.id,
            data.subdomain ?: "",
            data.reference_job_id ?: "",
            data.job_title ?: "",
            data.status ?: "",
            data.applied_date ?: "",
            data.current_job_title ?: "",
            data.current_company ?: "",
            data.working_since ?: "",
            data.have_assignment ?: false,
            data.assignment_submitted ?: false
        )
    }
}