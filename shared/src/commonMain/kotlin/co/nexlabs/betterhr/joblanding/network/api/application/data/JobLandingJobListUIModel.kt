package co.nexlabs.betterhr.joblanding.network.api.application.data

data class JobLandingJobListUIModel(
    val id: String,
    val company: CompanyInfoUIModel
)

data class CompanyInfoUIModel(
    val id: String,
    val companyName: String,
    val companyLogo: String
)