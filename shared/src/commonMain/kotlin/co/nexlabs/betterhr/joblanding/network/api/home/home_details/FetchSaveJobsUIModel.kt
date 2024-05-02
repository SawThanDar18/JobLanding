package co.nexlabs.betterhr.joblanding.network.api.home.home_details

data class FetchSaveJobsUIModel(
    val data: FetchSaveJobDatUIModel
)

data class FetchSaveJobDatUIModel(
    val id: String,
    val jobId: String,
    val candidateId: String
)