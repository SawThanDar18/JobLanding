package co.nexlabs.betterhr.joblanding.network.api.home.home_details

data class CollectionCompaniesUIModel(
    val id: String,
    val collectionId: String,
    val stateName: String,
    val jobOpeningCount: Int,
    val lastViewCount: Int,
    val lastConversationRate: Int,
    val lastCVCount: Int,
    val company: CompaniesCompanyUIModel
)

data class CompaniesCompanyUIModel(
    val id: String,
    val name: String,
    val logo: String
)