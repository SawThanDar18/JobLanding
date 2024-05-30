package co.nexlabs.betterhr.joblanding.network.api.home.home_details

data class CompanyDetailUIModel(
    val id: String,
    val subDomain: String,
//    val country: String,
    val name: String,
    val description: String,
    val companyMail: String,
    val companyLink: String,
    val jobCounts: Int,
    //val jobs: List<CompanyDetailJobs>
)

