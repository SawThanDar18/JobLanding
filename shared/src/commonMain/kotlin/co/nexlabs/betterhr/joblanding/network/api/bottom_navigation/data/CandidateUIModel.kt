package co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data

data class CandidateUIModel(
    val emailVerifiedAt: String,
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
    val companies: List<CompaniesUIModel>,
    val education: List<EducationUIModel>,
    val language: List<LanguageUIModel>,
    val skill: List<SkillUIModel>,
    val certificate: List<CertificateUIModel>
)

data class CertificateUIModel(
    val id: String,
    val candidateId: String,
    val courseName: String,
    val issuingOrganization: String,
    val issueDate: String,
    val expireDate: String,
    val isExpire: Boolean,
    val credentialUrl: String
)

data class SkillUIModel(
    val id: String,
    val skillName: String
)

data class LanguageUIModel(
    val id: String,
    val languageName: String,
    val proficiencyLevel: String
)

data class EducationUIModel(
    val id: String,
    val countryName: String,
    val university: String,
    val educationLevel: String,
    val degree: String,
    val fieldOfStudy: String,
    val startDate: String,
    val endDate: String,
    val isCurrentStudying: Boolean,
    val description: String
)

data class CompaniesUIModel(
    val id: String,
    val name: String,
    val file: FilesUIModel,
    val experience: List<ExperienceUIModel>
)

data class ExperienceUIModel(
    val id: String,
    val positionId: String,
    val candidateId: String,
    val title: String,
    val location: String,
    val experienceLevel: String,
    val employmentType: String,
    val startDate: String,
    val endDate: String,
    val isCurrentJob: Boolean,
    val description: String,
    val companyId: String,
    val position: PositionUIModel
)

data class PositionUIModel(
    val id: String,
    val name: String
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