package co.nexlabs.betterhr.joblanding.network.api.request_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileUploadResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("path") val path: String,
    @SerialName("folder") val folder: String,
    @SerialName("full_path") val fullPath: String,
    @SerialName("size") val size: String,

)