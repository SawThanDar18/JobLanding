package co.nexlabs.betterhr.joblanding.network.api.application.data

data class ApplicationByIdUIModel(
    val id: String,
    val subDomain: String,
    val referenceJobId: String,
    val jobTitle: String,
    val status: String,
    val appliedDate: String,
    val currentJobTitle: String,
    val currentCompany: String,
    val workingSince: String,
    val applicationHistories: List<ApplicationHistoryUIModel>
)

data class ApplicationHistoryUIModel(
    val id: String,
    val applicationStatus: String,
    val applicationDate: String,
    val haveAssignment: Boolean,
    val isAssignmentSubmmitted: Boolean
)