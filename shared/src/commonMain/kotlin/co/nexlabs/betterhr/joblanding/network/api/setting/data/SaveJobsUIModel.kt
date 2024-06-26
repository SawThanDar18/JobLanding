package co.nexlabs.betterhr.joblanding.network.api.setting.data

data class SaveJobsUIModel(
    val id: String,
    val savedJobsCompanyUIModel: SavedJobsCompanyUIModel,
    val title: String,
    val createdBy: String,
    val stateName: String,
    val hiringDate: String,
    val minSalary: Double,
    val maxSalary: Double,
    val currencyCode: String
)

data class SavedJobsCompanyUIModel(
    val id: String,
    val companyName: String,
    val companyLogo: String
)