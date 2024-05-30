package co.nexlabs.betterhr.joblanding.network.api.application.data

data class ApplicationUIModel(
    val id: String,
    val subDomain: String,
    val referenceJobId: String,
    val jobTitle: String,
    val status: String,
    val appliedDate: String,
    val currentJobTitle: String,
    val currentCompany: String,
    val workingSince: String,
    val haveAssignment: Boolean,
    val isAssignmentSubmmitted: Boolean
)


