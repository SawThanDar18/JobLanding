package co.nexlabs.betterhr.joblanding.network.api.request_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExistingFileIdTypeObject(
    @SerialName("ids") val type: List<String>
)