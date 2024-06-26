package co.nexlabs.betterhr.joblanding.android.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ConvertYMDToMY(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM yyyy", Locale.getDefault())

    val date: Date = inputFormat.parse(inputDate) ?: return inputDate

    return outputFormat.format(date)
}