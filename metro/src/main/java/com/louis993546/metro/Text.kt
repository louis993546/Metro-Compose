package com.louis993546.metro

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

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
    size: TextUnit = 20.sp,
    lineHeight: TextUnit = 24.sp,
    weight: FontWeight = FontWeight.Normal,
    maxLine: Int = Int.MAX_VALUE,
) {
    BasicText(
        modifier = modifier,
        text = text,
        style = TextStyle.Default.copy(
            fontFamily = fontFamily,
            color = color,
            fontSize = size,
            fontWeight = weight,
            lineHeight = lineHeight,
        ),
        maxLines = maxLine,
    )
}

@Composable
fun Disclaimer(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = LocalTextOnBackgroundColor.current.copy(alpha = 0.35f),
    size: TextUnit = 18.sp,
    lineHeight: TextUnit = 20.sp,
    weight: FontWeight = FontWeight.Normal,
) {
    BasicText(
        modifier = modifier,
        text = text,
        style = TextStyle.Default.copy(
            fontFamily = fontFamily,
            color = color,
            fontSize = size,
            fontWeight = weight,
            lineHeight = lineHeight,
        ),
    )
}
