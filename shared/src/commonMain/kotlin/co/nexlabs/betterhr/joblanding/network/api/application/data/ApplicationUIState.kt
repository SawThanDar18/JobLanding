package co.nexlabs.betterhr.joblanding.network.api.application.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class ApplicationUIState(
    val isSuccessGetApplicationData: Boolean = false,
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val application: List<ApplicationUIModel> = ArrayList(),
    val applicationById: ApplicationByIdUIModel = ApplicationByIdUIModel(
        "", "", "", "", "", "", "", "", "", emptyList()
    ),
    val companyData: List<JobLandingJobListUIModel> = ArrayList()
)
