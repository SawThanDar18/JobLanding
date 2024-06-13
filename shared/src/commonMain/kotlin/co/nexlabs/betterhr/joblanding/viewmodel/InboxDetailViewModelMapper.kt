package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.FetchNotificationByIdQuery
import co.nexlabs.betterhr.joblanding.AssetProvider
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxDetailUIModel

object InboxDetailViewModelMapper {

    private lateinit var context: AssetProvider

    fun setContext(context: AssetProvider) {
        this.context = context
    }

    fun mapDataToInboxDetail(data: FetchNotificationByIdQuery.Data): InboxDetailUIModel {
        return data.notification.let { data ->
            InboxDetailUIModel(
                data!!.id ?: "",
                data.candidate_id ?: "",
                data.job_id ?: "",
                data.reference_id ?: "",
                data.reference_application_id ?: "",
                data.title ?: "",
                data.status ?: "",
                data.sub_domain ?: "",
                data.sender_name ?: "",
                data.sender_position ?: "",
                data.updated_at ?: "",
                //data.render_view ?: "",
                generateHTML(data.render_view ?: ""),
                data.interview_type ?: "",
                data.offer_link ?: "",
                data.interview_link ?: ""
            )
        }
    }

    private fun generateHTML(rawHTMLFromServer: String): String {
        try {
            val css = context.getAssetContent("NotificationUIConfig2.css")
            val html = context.getAssetContent("NotificationUIContainer2.html")
                .replace("<\$NOTIFICATION_BODY$>", rawHTMLFromServer)
                .replace("<\$STYLES$>", css)
            return html
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rawHTMLFromServer
    }
}