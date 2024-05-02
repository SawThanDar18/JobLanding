package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.CandidateQuery
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel

object CandidateViewModelMapper {
    fun mapDataToViewModel(data: CandidateQuery.Me): CandidateUIModel {
        return CandidateUIModel(
            data.id ?: "",
            data.name,
            data.email ?: "",
            data.phone,
            data.summary ?: "",
            data.desired_position ?: "",
            data.country_id ?: "",
            emptyList(),
            emptyList()
        )
    }
}