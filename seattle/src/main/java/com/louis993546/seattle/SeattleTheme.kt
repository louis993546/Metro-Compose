package com.louis993546.seattle

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.louis993546.metro.MetroTheme

@Composable
fun SeattleTheme(
    content: @Composable () -> Unit,
) {
    MetroTheme(
        accentColor = Color.Cyan,
        content = content,
    )
}
