package co.nexlabs.betterhr.joblanding.network.api.setting.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class SavedJobsUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val savedJobIds: List<SaveJobsIdsUIModel> = ArrayList(),
    val isSuccessSavedJobsIds: Boolean = false,
    val savedJobList: List<SaveJobsUIModel> = ArrayList()
)
