package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class ProfileRegisterUIState(
    val isSuccessForCandidateId: Boolean = false,
    val isSuccessForBearerToken: Boolean = false,
    val candidateId: String = "",
    val bearerToken: String = "",
    val isLoading: Boolean = false,
    val error: UIErrorType = UIErrorType.Nothing,
)
