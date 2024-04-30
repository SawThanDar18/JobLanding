package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.without_auth.JobLandingCollectionCompaniesQuery
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionCompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CompaniesCompanyUIModel

object CollectionCompaniesViewModelMapper {

    fun mapResponseToViewModel(response: JobLandingCollectionCompaniesQuery.Data): List<CollectionCompaniesUIModel> {
        return response.jobLandingCollectionCompanies.data.map {
            mapDataToCollectionCompanies(it)
        }
    }

    private fun mapDataToCollectionCompanies(data: JobLandingCollectionCompaniesQuery.Data1): CollectionCompaniesUIModel {
        return CollectionCompaniesUIModel(
            data.id,
            data.collection_id,
            data.state_name,
            data.job_opening_count,
            data.last3_view_count,
            data.last3_conversation_rate,
            data.last3_cv_count,
            CompaniesCompanyUIModel(
                data.company!!.id,
                data.company.name
            )
        )
    }
}