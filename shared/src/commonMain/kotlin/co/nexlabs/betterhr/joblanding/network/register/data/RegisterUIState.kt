package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class RegisterUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val isSuccessForBearerToken: Boolean = false,
    val bearerToken: String = "",
    val isGetRequestOTPValue: Boolean = false,
    val getRequestOTPValue: String = "",
    val isGetVerifyOTPValue: Boolean = false,
    val getVerifyOTPValue: String = ""
)
