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
    val name: String,
    val logo: String
)

data class JobsListUIModel(
    val id: String,
    val companyId: String,
    val position: String,
    val departmentId: String,
    val locationId: String,
    val countryId: String,
    val createdBy: String,
    val stateId: String,
    val cityName: String,
    val stateName: String,
    val hiringDate: String,
    val seniorityLevel: String,
    val employmentType: String,
    val workplaceType: String,
    val officeAddress: String,
    val currencyCode: String,
    val miniSalary: Double,
    val maxiSalary: Double,
    val description: String,
    val requirement: String,
    val benefitsAndPerks: String,
    val overView: String,
    val status: String,
    val lastViewCount: Int,
    val lastConversationRate: Int,
    val lastCVCount: Int,
    val company: CompanyUIModel
)

data class CollectionUIModel(
    val id: String,
    val name: String,
    val createdAt: String,
    val type: String
)
