package co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data

data class CandidateUIModel(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val summary: String,
    val desiredPosition: String,
    val countryId: String,
    val profilePath: String,
    val cvFileName: String,
    val cvFilePath: String,
    val coverFileName: String,
    val coverFilePath: String,
    val profile: FilesUIModel,
    val cv: FilesUIModel,
    val coverLetter: FilesUIModel,
    val applications: List<ApplicationsUIModel>,
    val companies: List<CompaniesUIModel>
)

data class CompaniesUIModel(
    val id: String,
    val name: String,
    val file: CandidateCompanyUIModel
)

data class CandidateCompanyUIModel(
    val id: String,
    val name: String,
    val folder: String,
    val fullPath: String
)

data class CertificationsUIModel(
    val name: String
)

data class ApplicationsUIModel(
    val files: List<FilesUIModel>
)

data class FilesUIModel(
    val id: String,
    val name: String,
    val type: String,
    val fullPath: String
)