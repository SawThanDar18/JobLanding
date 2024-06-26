package co.nexlabs.betterhr.joblanding.network.api.home.home_details

data class CompanyDetailJobUIModel(
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
    val company: CompanyDetailCompanyUIModel
)

data class CompanyDetailCompanyUIModel(
    val id: String,
    val name: String,
    val logo: String
)