package co.nexlabs.betterhr.joblanding.network.register.data

data class ProfileRegisterUIModel(
    val data: AccountCreationData
)

data class AccountCreationData(
    val accountCreationData: AccountCreationCandidateDataUIModel
)

data class AccountCreationCandidateDataUIModel(
    val id: String,
    val name: String,
    val email: String
)