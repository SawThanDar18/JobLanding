package co.nexlabs.betterhr.joblanding.network.register

import co.nexlabs.betterhr.joblanding.ContinentQuery
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class RegisterUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,

    val phoneNumber: String? = null,
    val code: String? = null,
)
