package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.joblanding.JobLandingCompanyDetailQuery
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailCompanyUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailJobs
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompanyDetailUIModel

object CompanyDetailViewModelMapper {

    public fun mapDataToViewModel(data: JobLandingCompanyDetailQuery.JobLandingCompany): CompanyDetailUIModel {

        var collectionJobsList: MutableList<CompanyDetailJobs> = ArrayList()
        if (data.jobs != null) {
            data.jobs.map {
                collectionJobsList.add(
                    CompanyDetailJobs(
                        it.id,
                        it.company_id ?: "",
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
                            it.company!!.id,
                            it.company.name
                        )
                    )
                )
            }
        }

        return CompanyDetailUIModel(
            data.id,
            data.subdomain,
            data.country,
            data.name,
            data.description,
            data.company_mail,
            data.company_link,
            data.jobs_count ?: 0,
            collectionJobsList
        )
    }
}