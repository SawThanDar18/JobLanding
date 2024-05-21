package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.without_auth.JobLandingSectionsQuery
import co.nexlabs.betterhr.joblanding.network.api.home.data.CollectionCompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.CollectionUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.CompanyUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.JobsListUIModel

object HomeViewModelMapper {
    fun mapResponseToViewModel(response: JobLandingSectionsQuery.Data): List<HomeUIModel> {
        return response.jobLandingSections.map {
            mapDataToJobLandingSections(it)
        }
    }

    private fun mapDataToJobLandingSections(data: JobLandingSectionsQuery.JobLandingSection): HomeUIModel {

        var collectionJobsList: MutableList<JobsListUIModel> = ArrayList()
        if (data.jobs != null) {
            data.jobs.map {
                collectionJobsList.add(
                    JobsListUIModel(
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
                        CompanyUIModel(
                            it.company!!.id,
                            it.company.name
                        )
                    )
                )
            }
        }

        var collectionCompaniesList: MutableList<CollectionCompaniesUIModel> = ArrayList()
        if (data.collectionCompanies != null) {
            data.collectionCompanies.map {
                collectionCompaniesList.add(
                    CollectionCompaniesUIModel(
                        it.id ?: "",
                        it.collection_id ?: "",
                        it.state_name ?: "",
                        it.job_opening_count ?: 0,
                        it.last3_view_count ?: 0,
                        it.last3_conversation_rate ?: 0,
                        it.last3_cv_count ?: 0,
                        CompanyUIModel(
                            it.company!!.id,
                            it.company.name
                        )
                    )
                )
            }
        }

        return HomeUIModel(
            data.title,
            data.platform ?: "",
            data.collection_type!!.name,
            data.post_style!!.name,
            data.order,
            CollectionUIModel(
                data.collection!!.id,
                data.collection.name,
                data.collection.created_at,
                data.collection.type!!.name
            ),
            data.data_count ?: 0,
            collectionJobsList,
            collectionCompaniesList
        )
    }
}