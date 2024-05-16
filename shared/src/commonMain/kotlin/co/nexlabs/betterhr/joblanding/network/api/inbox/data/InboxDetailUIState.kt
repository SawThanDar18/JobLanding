package co.nexlabs.betterhr.joblanding.network.api.inbox.data

import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class InboxDetailUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val notificationDetail: InboxDetailUIModel = InboxDetailUIModel(
        "", "", "", "", "", "", "", "", "", "", "", ""
    ),
)