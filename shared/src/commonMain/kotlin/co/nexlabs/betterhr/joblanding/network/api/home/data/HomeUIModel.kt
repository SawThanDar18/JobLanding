package co.nexlabs.betterhr.joblanding.network.api.home.data

data class HomeUIModel(
    val title: String,
    val platform: String,
    val collectionType: String,
    val postStyle: String,
    val order: Int,
    val collection: CollectionUIModel,
    val dataCount: Int,
    val jobs: List<JobsListUIModel>?,
    val collectionCompanies: List<CollectionCompaniesUIModel>
)

data class CollectionCompaniesUIModel(
    val id: String,
    val collectionId: String,
    val stateName: String,
    val jobOpeningCount: Int,
    val lastViewCount: Int,
    val lastConversationRate: Int,
    val lastCVCount: Int,
    val company: CompanyUIModel
)

data class CompanyUIModel(
    val id: String,
    val name: String
)

data class JobsListUIModel(
    val id: String
)

data class CollectionUIModel(
    val id: String,
    val name: String,
    val createdAt: String,
    val type: String
)
