package co.nexlabs.betterhr.joblanding.network.api.request_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCountriesListResponse(
    @SerialName("data") val data: List<DataResponse>
)

@Serializable
data class DataResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("flag") val flag: String,
    @SerialName("country_code") val countryCode: String,
    @SerialName("currency_code") val currencyCode: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

