package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.FetchSavedJobsIdsQuery
import co.nexlabs.betterhr.joblanding.network.api.setting.data.SaveJobsIdsUIModel

object SavedJobsIdsViewModelMapper {

    fun mapDataToViewModel(data: FetchSavedJobsIdsQuery.Data): List<SaveJobsIdsUIModel> {
        return data.saveJobs.data.map {
            SaveJobsIdsUIModel(
                it.id ?: "",
                it.job_id ?: "",
            )
        }
    }
}