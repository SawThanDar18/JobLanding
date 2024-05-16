package co.nexlabs.betterhr.joblanding.network.api.inbox.data

data class AttachmentFileUIModel(
    val id: String,
    val name: String,
    val path: String,
    val folder: String,
    val fullPath: String,
    val size: String,
)