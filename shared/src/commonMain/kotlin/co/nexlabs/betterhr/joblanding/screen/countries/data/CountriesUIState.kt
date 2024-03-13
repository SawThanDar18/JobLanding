package co.nexlabs.betterhr.joblanding.screen.countries.data

import co.nexlabs.betterhr.joblanding.ContinentQuery
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class CountriesUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,

    val continentId: String? = null,
    val countries: List<ContinentQuery.Country> = listOf(),
    val continentCodeAndName: Pair<String, String>? = null
)
