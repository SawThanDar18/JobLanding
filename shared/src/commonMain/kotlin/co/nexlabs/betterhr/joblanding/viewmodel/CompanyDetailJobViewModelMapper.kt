package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.without_auth.JobLandingCompanyJobsQuery
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailCompanyUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailJobUIModel

object CompanyDetailJobViewModelMapper {
    
    fun mapDataToViewModel(data: JobLandingCompanyJobsQuery.Data): List<CompanyDetailJobUIModel> {

        return data.jobLandingCompanyJobs.map {
            CompanyDetailJobUIModel(
                it.id,
                it.company!!.id ?: "",
                it.title ?: "",
                it.department_id ?: "",
                it.location_id ?: "",
                it.country_id ?: "",
                it.created_by ?: "",
                it.state_id ?: "",
                it.city_name ?: "",
                it.state_name ?: "",
                it.hiring_date ?: "",
                it.seniority_level!!.name,
                "Full time",
                it.workplace_type!!.name,
                it.office_address ?: "",
                it.currency_code ?: "",
                it.min_salary ?: 0.0,
                it.max_salary ?: 0.0,
                it.description ?: "",
                it.requirement ?: "",
                it.benefits_and_perks ?: "",
                it.overview ?: "",
                it.status!!.name,
                it.last3_view_count ?: 0,
                it.last3_conversation_rate ?: 0,
                it.last3_cv_count ?: 0,
                CompanyDetailCompanyUIModel(
                    it.company.id,
                    it.company.name
                )
            )
        }
    }
}