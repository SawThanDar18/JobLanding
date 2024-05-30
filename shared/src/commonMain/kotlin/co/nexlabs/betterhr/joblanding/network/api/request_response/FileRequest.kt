package co.nexlabs.betterhr.joblanding.network.api.request_response

data class FileRequest(
    val fileType: List<FileType>
)

data class FileType(
    val type: String,
    val name: String
)