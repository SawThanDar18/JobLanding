package co.nexlabs.betterhr.joblanding.util

sealed class UIErrorType {
    data object Network : UIErrorType()
    data class Other(val error: String) : UIErrorType()
    data object Nothing : UIErrorType()

}

