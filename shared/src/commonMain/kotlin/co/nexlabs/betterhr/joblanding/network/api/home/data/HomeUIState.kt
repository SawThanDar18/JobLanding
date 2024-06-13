package co.nexlabs.betterhr.joblanding.network.api.home.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class HomeUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val jobLandingSectionsList: List<HomeUIModel> = ArrayList(),
)
