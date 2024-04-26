package co.nexlabs.betterhr.joblanding.network.register

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class VerifyUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val message: String = ""
)