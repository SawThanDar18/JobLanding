package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class CollectionCompaniesUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val collectionCompaniesList: List<CollectionCompaniesUIModel> = ArrayList(),
)
