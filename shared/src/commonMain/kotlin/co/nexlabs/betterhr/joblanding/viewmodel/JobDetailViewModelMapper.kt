package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.without_auth.JobLandingJobDetailQuery
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailCompanyUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailUIModel

object JobDetailViewModelMapper {

    fun mapDataToViewModel(data: JobLandingJobDetailQuery.JobLandingJob): JobDetailUIModel {

        return JobDetailUIModel(
            data.id,
            data.company_id ?: "",
            data.title ?: "",
            data.department_id ?: "",
            data.location_id ?: "",
            data.country_id ?: "",
            data.created_by ?: "",
            data.state_id ?: "",
            data.city_name ?: "",
            data.city_id ?: "",
            data.state_name ?: "",
            data.hiring_date ?: "",
            data.seniority_level!!.name,
            "Full time",
            data.workplace_type!!.name,
            data.office_address ?: "",
            data.currency_code ?: "",
            data.min_salary ?: 0.0,
            data.max_salary ?: 0.0,
            data.description ?: "",
            data.requirement ?: "",
            data.benefits_and_perks ?: "",
            data.overview ?: "",
            data.status!!.name,
            data.last3_view_count ?: 0,
            data.last3_conversation_rate ?: 0,
            data.last3_cv_count ?: 0,
            JobDetailCompanyUIModel(
                data.company!!.id,
                data.company.name
            )
        )
    }
}