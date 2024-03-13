package co.nexlabs.betterhr.joblanding.screen.continents.data

import co.nexlabs.betterhr.joblanding.ContinentsQuery
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class ContinentsUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val continents: List<ContinentsQuery.Continent> = listOf()
)
