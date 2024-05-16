package co.nexlabs.betterhr.joblanding.network.api.inbox.data

data class InboxDetailUIModel(
    val id: String,
    val candidateId: String,
    val jobId: String,
    val referenceId: String,
    val referenceApplicationId: String,
    val title: String,
    val status: String,
    val subDomain: String,
    val senderName: String,
    val senderPosition: String,
    val updatedAt: String,
    val renderView: String
)
