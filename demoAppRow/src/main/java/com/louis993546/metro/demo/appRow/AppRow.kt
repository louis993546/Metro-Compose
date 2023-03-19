package com.louis993546.metro.demo.appRow

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.Text

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier.size(48.dp), content = content)
}

@Composable
fun AppRow(
    modifier: Modifier = Modifier,
    appName: String,
    appIcon: Drawable?,
) {
    Row(modifier = modifier) {
        AppIcon {
            when (appIcon) {
                null -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = LocalAccentColor.current)
                )
                else -> Box(
                    modifier = Modifier
                        .background(color = LocalAccentColor.current)
                        .padding(8.dp),
                ) {
                    Image(
                        painter = rememberDrawablePainter(drawable = appIcon),
                        contentDescription = appName,
                    )
                }
            }

        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = appName,
            size = 28.sp,
        )
    }
}
