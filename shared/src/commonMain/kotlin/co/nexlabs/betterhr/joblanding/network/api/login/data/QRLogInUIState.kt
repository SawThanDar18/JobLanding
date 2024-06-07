package co.nexlabs.betterhr.joblanding.network.api.login.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class QRLogInUIState (
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val isSuccessQRLogIn: Boolean = false
)