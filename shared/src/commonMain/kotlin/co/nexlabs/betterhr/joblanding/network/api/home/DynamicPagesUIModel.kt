package co.nexlabs.betterhr.joblanding.network.api.home

data class DynamicPagesUIModel(
    val data: DynamicPageData
)

data class DynamicPageData(
    val dynamicPagesListData: List<DynamicPagesListData>
)

data class DynamicPagesListData(
    val id: String,
    val name: String
)