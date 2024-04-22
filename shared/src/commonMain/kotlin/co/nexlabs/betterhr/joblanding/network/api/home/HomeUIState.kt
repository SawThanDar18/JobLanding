package co.nexlabs.betterhr.joblanding.network.api.home

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class HomeUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
)
