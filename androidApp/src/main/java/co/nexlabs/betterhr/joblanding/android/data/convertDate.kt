package co.nexlabs.betterhr.joblanding.android.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun convertDate(inputDate: String): String {
    //val userInput = "September 2024"
    // Parse the input date string
    val date = LocalDate.parse(inputDate, DateTimeFormatter.ofPattern("MMMM uuuu", Locale.ENGLISH))

    // Format the date to the desired format
    val formattedDate = date.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"))

    return formattedDate
}