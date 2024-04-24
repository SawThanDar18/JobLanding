package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class JobDetailUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val jobDetail: JobDetailUIModel = JobDetailUIModel(
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 0.0, 0.0, "", "", "", "", "",
        0, 0, 0, JobDetailCompanyUIModel(
            "", ""
        )
    )
)