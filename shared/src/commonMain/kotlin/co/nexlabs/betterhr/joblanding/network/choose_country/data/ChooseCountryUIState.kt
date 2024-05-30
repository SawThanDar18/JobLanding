package co.nexlabs.betterhr.joblanding.network.choose_country.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class ChooseCountryUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val dynamicPageId: String = "",
    val countries: MutableList<Data> = ArrayList(),
    val items: MutableList<Item> = ArrayList()
)
