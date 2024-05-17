package co.nexlabs.betterhr.joblanding.viewmodel

import android.content.Context
import android.util.Log
import co.nexlabs.betterhr.job.with_auth.FetchNotificationByIdQuery
import co.nexlabs.betterhr.job.with_auth.FetchNotificationsQuery
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxDetailUIModel
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxUIModel
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets

object InboxDetailViewModelMapper {

    private lateinit var context: Context

    fun setContext(context: Context) {
        this.context = context
    }

    fun mapDataToInboxDetail(data: FetchNotificationByIdQuery.Notification): InboxDetailUIModel {
        return InboxDetailUIModel(
            data.id ?: "",
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
            generateHTML(data.render_view ?: ""),
            data.interview_type ?: "",
            data.offer_link ?: ""
        )
    }

    private fun generateHTML(rawHTMLFromServer: String): String {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(
                InputStreamReader(
                    context.assets.open("NotificationUIConfig2.css"),
                    StandardCharsets.UTF_8
                )
            )
            val cssBuilder = StringBuilder()
            var cssLine: String?
            while (reader.readLine().also { cssLine = it } != null) {
                cssBuilder.append(cssLine)
            }
            reader = BufferedReader(
                InputStreamReader(
                    context.assets.open("NotificationUIContainer2.html"),
                    StandardCharsets.UTF_8
                )
            )
            val htmlBuilder = StringBuilder()
            var htmlLine: String?
            while (reader.readLine().also { htmlLine = it } != null) {
                htmlBuilder.append(htmlLine)
            }
            var html = htmlBuilder.toString()
            val css = cssBuilder.toString()
            html = html.replace("<\$NOTIFICATION_BODY$>", rawHTMLFromServer)
            html = html.replace("<\$STYLES$>", css)
            return html
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (reader != null) {
                try {
                    reader.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
        return rawHTMLFromServer
    }
}