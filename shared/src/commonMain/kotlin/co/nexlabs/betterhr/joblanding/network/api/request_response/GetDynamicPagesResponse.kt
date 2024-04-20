package co.nexlabs.betterhr.joblanding.network.api.request_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetDynamicPagesRequest(val countryId: String, val platform: String) {
    @SerialName("query")
    val query = "dynamicPages(country_id: \$country_id, platform: \$platform)\n" +
            "    {\n" +
            "        id\n" +
            "        name\n" +
            "    }"

    @SerialName("variables")
    val variables = mapOf("country_id" to countryId, "platform" to platform)
}

@Serializable
data class GetDynamicPagesResponse(@SerialName("data") val data: DynamicPageDataResponse) {

    @Serializable
    data class DynamicPageDataResponse(
        @SerialName("dynamicPages") val dynamicPages: List<DynamicPagesResponse>
    )
}

@Serializable
data class DynamicPagesResponse(
    @SerialName("id") val id : String,
    @SerialName("name") val name: String
)

