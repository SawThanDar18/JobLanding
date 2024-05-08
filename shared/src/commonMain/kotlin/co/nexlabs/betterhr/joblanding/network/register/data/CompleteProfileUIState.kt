package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class CompleteProfileUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val candidateData: CandidateUIModel = CandidateUIModel(
        "", "", "", "", "", "", "", "", "", "", "", "",
        emptyList(), emptyList()
    )
)
