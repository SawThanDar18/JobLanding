package co.nexlabs.betterhr.joblanding.network.api.request_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetCountriesListResponse(
    @SerialName("status") val status: String,
    @SerialName("message") val message: String,
    @SerialName("data") val data: List<DataResponse>
)

@Serializable
data class DataResponse(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("flag") val flag: String,
    @SerialName("country_code") val countryCode: String,
    @SerialName("currency_code") val currencyCode: String
)

