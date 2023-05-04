package com.louis993546.metro

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private fun Int.textDp(density: Density): TextUnit = with(density) {
    this@textDp.dp.toSp()
}
private val Int.textDp: TextUnit
    @Composable get() =  this.textDp(density = LocalDensity.current)
private fun Float.textDp(density: Density): TextUnit = with(density) {
    this@textDp.dp.toSp()
}
private val Float.textDp: TextUnit
    @Composable get() =  this.textDp(density = LocalDensity.current)

@Composable
fun FromAdaptiveTextStyle(
    style: AdaptiveTextStyle,
    align: AdaptiveTextAlign = AdaptiveTextAlign.Left,
): TextStyle {
    // Exists to let the number better match the documentation
    val scale = 1.5f

    // https://learn.microsoft.com/en-us/windows/apps/design/shell/tiles-and-notifications/create-adaptive-tiles#text-styles
    val subtle_color = LocalTextOnAccentColor.current.copy(alpha = 0.6f)
    val default = TextStyle.Default.copy(
        fontFamily = fontFamily,
        color = LocalTextOnAccentColor.current,
        fontSize = 10.textDp * scale,
        lineHeight = 12.textDp * scale,
        fontWeight = FontWeight.Normal,
        textAlign = FromAdaptiveTextAlign(align),

        lineHeightStyle = LineHeightStyle(
            trim = LineHeightStyle.Trim.Both,
            alignment = LineHeightStyle.Alignment.Bottom,
        )
    )

    val caption = default.copy()

    val body = default.copy(
        fontSize = 16.textDp * scale,
        lineHeight = 18.textDp * scale,
    )

    val base = body.copy(
        fontWeight = FontWeight.SemiBold,
    )

    val subtitle = body.copy(
        fontSize = 18.textDp * scale,
        lineHeight = 20.textDp * scale,
    )

    val title = subtitle.copy(
        fontSize = 32.textDp * scale,
        lineHeight = 36.textDp * scale,
        fontWeight = FontWeight.Normal,
    )

    val subheader = subtitle.copy(
        fontSize = 32.textDp * scale,
        lineHeight = 36.textDp * scale,
        fontWeight = FontWeight.Light,
    )

    val header = subtitle.copy(
        fontSize = 42.textDp * scale,
        lineHeight = 46.textDp * scale,
    )

    return when(style) {
        AdaptiveTextStyle.Default -> default

        AdaptiveTextStyle.Caption -> caption
        AdaptiveTextStyle.CaptionSubtle -> caption.copy(color = subtle_color)

        AdaptiveTextStyle.Body -> body
        AdaptiveTextStyle.BodySubtle -> body.copy(color = subtle_color)

        AdaptiveTextStyle.Base -> base
        AdaptiveTextStyle.BaseSubtle -> base.copy(color = subtle_color)

        AdaptiveTextStyle.Subtitle -> subtitle
        AdaptiveTextStyle.SubtitleSubtle -> subtitle.copy(color = subtle_color)

        AdaptiveTextStyle.Title -> title
        AdaptiveTextStyle.TitleSubtle -> title.copy(color = subtle_color)
        AdaptiveTextStyle.TitleNumeral -> title.copy(lineHeight = 28.sp)

        AdaptiveTextStyle.Subheader -> subheader
        AdaptiveTextStyle.SubheaderSubtle -> subheader.copy(color = subtle_color)
        AdaptiveTextStyle.SubheaderNumeral -> subheader.copy(lineHeight = 34.sp)

        AdaptiveTextStyle.Header -> header
        AdaptiveTextStyle.HeaderSubtle -> header.copy(color = subtle_color)
        AdaptiveTextStyle.HeaderNumeral -> header.copy(lineHeight = 40.sp)

        // TODO
        else -> {
            default
        }
    }
}

@Composable
fun FromAdaptiveTextAlign(
    align: AdaptiveTextAlign
): TextAlign {
    return when(align) {
        AdaptiveTextAlign.Default -> TextAlign.Left
        AdaptiveTextAlign.Auto -> TextAlign.Left // TODO: Should be the user's locale one
        AdaptiveTextAlign.Left -> TextAlign.Left
        AdaptiveTextAlign.Center -> TextAlign.Center
        AdaptiveTextAlign.Right -> TextAlign.Right
    }
}
