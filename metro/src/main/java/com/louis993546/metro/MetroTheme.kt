package com.louis993546.metro

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val LocalAccentColor = compositionLocalOf<Color> { error("No accent color found!") }
val LocalBackgroundColor = compositionLocalOf<Color> { error("No background color found") }
val LocalTextOnAccentColor = compositionLocalOf<Color> { error("No text on accent color found") }
val LocalTextOnBackgroundColor =
    compositionLocalOf<Color> { error("No text on background color found") }
val LocalButtonColor = compositionLocalOf<Color> { error("No button color found") }
val LocalTextOnButtonColor = compositionLocalOf<Color> { error("No text on button color found") }

@Composable
fun MetroTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    accentColor: Color,
    content: @Composable () -> Unit
) {
    val backgroundColor = if (darkTheme) Color.Black else Color.White
    val textOnBackgroundColor = if (darkTheme) Color.White else Color.Black
    val textOnAccentColor = accentColor.textColor()
    val buttonColor = if (darkTheme) Color(0xFF1F1F1F) else Color(0xFFDDDDDD)

    CompositionLocalProvider(
        LocalAccentColor provides accentColor,
        LocalBackgroundColor provides backgroundColor,
        LocalTextOnAccentColor provides textOnAccentColor,
        LocalTextOnBackgroundColor provides textOnBackgroundColor,
        LocalButtonColor provides buttonColor,
        LocalTextOnButtonColor provides textOnBackgroundColor, // this is close enough for now
    ) {
        content()
    }
}

private fun Color.textColor(): Color {
    val luminance = this.luminance()
    return if (luminance >= 0.5) Color.Black else Color.White
}
