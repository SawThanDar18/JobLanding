package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.FilesUIModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class CompleteProfileUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val candidateData: CandidateUIModel = CandidateUIModel(
        "", "", "", "", "", "", "", "", "", "", "", "",
        FilesUIModel("", "", "", ""),FilesUIModel("", "", "", ""),FilesUIModel("", "", "", ""),
        emptyList(), emptyList()
    ),
    val isSuccessUpdateSummary: Boolean = false,
    val isSuccessCreateCompany: Boolean = false,
    val getFileId: Boolean = false,
    val fileId: String = "",
    val isSuccessCreateExperience: Boolean = false,
    val isSuccessUpdateExperience: Boolean = false,
)
