package co.nexlabs.betterhr.joblanding.android.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import co.nexlabs.betterhr.joblanding.android.R

@Composable
fun TextCompose(text: String, fontWeight: FontWeight, textColor: Color, fontSize: TextUnit) {
    Text(
        text = text,
        fontFamily = FontFamily(Font(R.font.poppins_regular)),
        fontWeight = fontWeight,
        color = textColor,
        fontSize = fontSize
    )
}