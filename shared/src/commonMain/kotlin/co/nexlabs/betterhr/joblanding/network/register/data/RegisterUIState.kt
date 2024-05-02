package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class RegisterUIState(
    val successData: String = "",
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val message: String = ""
)
