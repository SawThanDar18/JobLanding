package co.nexlabs.betterhr.joblanding.network.api.home.home_details

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class CompanyDetailUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val companyDetail: CompanyDetailUIModel = CompanyDetailUIModel(
        "", "", "", "", "", "",
        0, "", ""
    ),
    val isSuccessGetCompanyDetail: Boolean = false,
    val companyDetailJobList: List<CompanyDetailJobUIModel> = emptyList()
)
