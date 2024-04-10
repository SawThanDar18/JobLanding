package co.nexlabs.betterhr.joblanding.network.choose_country.data

data class CountriesListUIModel(
    val status: String,
    val message: String,
    val data: List<Data>
)

data class Data(
    val id: String,
    val countryName: String
)