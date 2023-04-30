package com.louis993546.seattle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalTextOnAccentColor
import com.louis993546.metro.Text

@Composable
fun HomeTile(
    modifier: Modifier = Modifier,
    app: App,
    backgroundColor: Color = LocalAccentColor.current,
    textColor: Color = LocalTextOnAccentColor.current,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = backgroundColor)
            .clickable(onClick = onClick)
    ) {
        Image(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.Center),
            painter = rememberDrawablePainter(drawable = app.iconDrawable),
            contentDescription = app.label,
            colorFilter = ColorFilter.tint(textColor),
        )

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 6.dp, bottom = 2.dp),
            text = app.label,
            color = textColor,
        )
    }
}

@Preview
@Composable
fun PreviewHomeTile() {
    HomeTile(
        app = App(
            label = "App",
            iconDrawable = TODO(),
            packageName = "",
        ),
        backgroundColor = Color.Black,
        textColor = Color.White,
        onClick = { },
    )
}
