package co.nexlabs.betterhr.joblanding.network.api.inbox.data

data class InboxUIModel(
    val id: String,
    val candidateId: String,
    val referenceId: String,
    val jobId: String,
    val title: String,
    val status: String,
    val notiType: String,
    val updateAt: String
)