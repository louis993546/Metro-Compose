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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
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
    Box(
        modifier = modifier
            .size(56.dp),
        content = content
    )
}

@Composable
fun AppRow(
    modifier: Modifier = Modifier,
    name: String,
    icon: Drawable? = null,
    background: Color = LocalAccentColor.current,
) {
    Row(modifier = modifier.fillMaxSize()) {
        AppIcon {
            when (icon) {
                null -> Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = background)
                ) else -> Box(
                    modifier = Modifier
                        .background(color = background)
                        .padding(2.dp),
                ) {
                    Image(
                        painter = rememberDrawablePainter(drawable = icon),
                        contentDescription = name,
                        colorFilter = ColorFilter.tint(color = Color.White)
                    )
                }
            }

        }
        Spacer(modifier = Modifier.width(14.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically).padding(bottom = 4.dp),
            text = name,
            size = 28.sp,
            weight = FontWeight.Light
        )
    }
}
