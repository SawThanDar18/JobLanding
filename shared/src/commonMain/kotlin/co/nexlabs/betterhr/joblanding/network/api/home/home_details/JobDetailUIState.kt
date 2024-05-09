package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class JobDetailUIState(
    val isSuccessUpdateApplication: Boolean = false,
    val isSuccessForCandidateId: Boolean = false,
    val isSuccessForBearerToken: Boolean = false,
    val candidateId: String = "",
    val bearerToken: String = "",
    val saveJobSuccessMsg: String = "",
    var isApplyJobSuccess: Boolean = false,
    var isSaveJobSuccess: Boolean = false,
    val isUnSaveJobSuccess: Boolean = false,
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val jobDetail: JobDetailUIModel = JobDetailUIModel(
        "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", 0.0, 0.0, "", "", "", "", "",
        0, 0, 0,
        JobDetailCompanyUIModel(
            "", "", ""
        )
    ),
    val fetchSaveJobs: FetchSaveJobsUIModel = FetchSaveJobsUIModel(
        data = FetchSaveJobDatUIModel("", "", "")
    ),
    val candidateData: CandidateUIModel = CandidateUIModel(
        "", "", "", "", "", "", "", "", "", "", "", "",
        emptyList(), emptyList()
    ),
    val idFromProfileUpload: String = "",
    val idFromCreateApplication: String = ""
)
