package co.nexlabs.betterhr.joblanding.network.api.interview.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class InterviewUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val interviewList: List<InterviewUIModel> = ArrayList(),
)