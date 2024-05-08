package co.nexlabs.betterhr.joblanding.android.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CurrentDateTimeFormatted(): String {
    val currentDateTime = remember { LocalDateTime.now() }
    val formatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss") }
    return currentDateTime.format(formatter)
}
