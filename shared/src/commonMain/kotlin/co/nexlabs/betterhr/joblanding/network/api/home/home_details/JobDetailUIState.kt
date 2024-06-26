package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.FilesUIModel
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileUploadResponse
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class JobDetailUIState(
    val fetchSaveJobId: String = "",
    val isSuccessGetJobDetail: Boolean = false,
    val isBearerTokenExist: Boolean = false,
    val isSuccessUpdateApplication: Boolean = false,
    val isSuccessCreateApplication: Boolean = false,
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
            "", "", "", "", ""
        )
    ),
    var fetchSaveJobs: FetchSaveJobsUIModel = FetchSaveJobsUIModel(
        data = FetchSaveJobDatUIModel("", "", "")
    ),
    val candidateData: CandidateUIModel = CandidateUIModel(
        "", "", "", "", "", "", "", "", "", "", "", "",
        FilesUIModel("", "", "", ""),FilesUIModel("", "", "", ""),FilesUIModel("", "", "", ""),
        emptyList(), emptyList(), emptyList(), emptyList(), emptyList(), emptyList()
    ),
    val idFromProfileUpload: String = "",
    val idFromCreateApplication: String = "",
    val isSuccessUploadMultipleFile: Boolean = false,
    val multiFileList: List<FileUploadResponse> = ArrayList(),
    val appliedJobStatus: String = "",
    val isGetRequestOTPValue: Boolean = false,
    val getRequestOTPValue: String = "",
    val isGetVerifyOTPValue: Boolean = false,
    val getVerifyOTPValue: String = ""
)
