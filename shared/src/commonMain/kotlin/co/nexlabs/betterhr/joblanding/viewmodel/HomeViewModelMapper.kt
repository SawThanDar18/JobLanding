package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.joblanding.JobLandingSectionsQuery
import co.nexlabs.betterhr.joblanding.network.api.home.data.CollectionCompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.CollectionUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.CompanyUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeUIModel

object HomeViewModelMapper {
    fun mapResponseToViewModel(response: JobLandingSectionsQuery.Data): List<HomeUIModel> {
        return response.jobLandingSections.map {
            mapDataToJobLandingSections(it)
        }
    }

    private fun mapDataToJobLandingSections(data: JobLandingSectionsQuery.JobLandingSection): HomeUIModel {
        var collectionCompaniesList: MutableList<CollectionCompaniesUIModel> = ArrayList()
        if (data.collectionCompanies != null) {
            data.collectionCompanies.map {
                collectionCompaniesList.add(
                    CollectionCompaniesUIModel(
                        it.id,
                        it.collection_id,
                        it.state_name,
                        it.job_opening_count,
                        it.last3_view_count,
                        it.last3_conversation_rate,
                        it.last3_cv_count,
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
            2,
            emptyList(),
            collectionCompaniesList
        )
    }
}