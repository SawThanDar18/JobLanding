package co.nexlabs.betterhr.joblanding.android.data

import java.text.SimpleDateFormat
import java.util.*

fun convertDate(inputDate: String): String {
    //val userInput = "September 2024"
    val parsedDate = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).parse(inputDate)
    val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(parsedDate ?: Date())
    return formattedDate
}