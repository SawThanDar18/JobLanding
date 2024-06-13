package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.FetchNotificationsQuery
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxUIModel

object InboxViewModelMapper {
    fun mapResponseToViewModel(data: FetchNotificationsQuery.Data): List<InboxUIModel> {
        return data.notifications.map {
            mapDataToInbox(it)
        }
    }

    fun mapDataToInbox(data: FetchNotificationsQuery.Notification): InboxUIModel {
        return InboxUIModel(
            data.id ?: "",
            data.candidate_id ?: "",
            data.reference_id ?: "",
            data.job_id ?: "",
            data.title ?: "",
            data.status ?: "",
            data.noti_type ?: "",
            data.updated_at ?: ""
        )
    }
}