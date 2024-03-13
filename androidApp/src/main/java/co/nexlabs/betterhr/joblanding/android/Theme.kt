package co.nexlabs.betterhr.joblanding.android

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color.Gray,
    primaryVariant = Color.Green,
    secondary = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Color.Gray,
    primaryVariant = Color.Green,
    secondary = Color.Black
)

@Composable
fun CustomOTPTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}