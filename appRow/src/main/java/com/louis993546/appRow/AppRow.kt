package com.louis993546.appRow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
) {
    Row(modifier = modifier) {
        AppIcon {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LocalAccentColor.current)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = appName,
            size = 28.sp,
        )
    }
}
