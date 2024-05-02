package co.nexlabs.betterhr.joblanding.network.api.apply_job.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class ApplyJobUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing
)
