package com.louis993546.metro

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * Note: If you want it to look more like Metro, you can import Segoe UI font and override it. But
 * please make sure you are following it's license. Visit Microsoft Fluent UI website for the
 * latest info.
 */
@Composable
fun Text(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = LocalTextOnBackgroundColor.current,
) {
    BasicText(
        modifier = modifier,
        text = text,
        style = TextStyle.Default.copy(
            fontFamily = fontFamily,
            color = color,
        ),
    )
}

