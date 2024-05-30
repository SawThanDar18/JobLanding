package co.nexlabs.betterhr.joblanding.android.screen
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun ResponseMessage(responseMessage: String) {
    val visible = responseMessage != null

    if (visible) {
        var message = responseMessage!!
        LaunchedEffect(message) {
            delay(2000) // Adjust the duration as needed
        }

        Surface(
            color = Color(0x99000000),
            shape = RectangleShape,
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
               // modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = message,
                    style = MaterialTheme.typography.body1,
                    color = Color.Black,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}
