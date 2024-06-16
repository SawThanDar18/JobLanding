package co.nexlabs.betterhr.joblanding.network.choose_country.data

data class CountriesListUIModel(
    val status: String,
    val message: String,
    val data: List<CountryData>
)

data class CountryData(
    val id: String,
    val countryName: String
)