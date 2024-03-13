package co.nexlabs.betterhr.joblanding.screen.country.data

import co.nexlabs.betterhr.joblanding.CountryQuery
import co.nexlabs.betterhr.joblanding.util.UIErrorType


enum class CurrentBottomSheetContent {
    STATES, LANGUAGES
}

data class CountryUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,

    val countryCode: String? = null,
    val data: CountryQuery.Country? = null, // Cuz, i'm a lazy *****
    val currentBottomSheetContent: CurrentBottomSheetContent = CurrentBottomSheetContent.STATES
)
