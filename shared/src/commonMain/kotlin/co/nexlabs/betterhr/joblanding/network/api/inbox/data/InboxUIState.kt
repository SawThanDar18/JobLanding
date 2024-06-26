package co.nexlabs.betterhr.joblanding.network.api.inbox.data

import co.nexlabs.betterhr.joblanding.network.api.application.data.JobLandingJobListUIModel
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class InboxUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val notificationList: List<InboxUIModel> = ArrayList(),
    val isSuccessGetInboxData: Boolean = false,
    val companyData: List<JobLandingJobListUIModel> = ArrayList()
)