package com.louis993546.metro.ui.theme

import androidx.compose.runtime.Composable
import com.louis993546.metro.MetroColor
import com.louis993546.metro.MetroTheme

@Composable
fun MetroDemoTheme(content: @Composable () -> Unit) {
    MetroTheme(accentColor = MetroColor.cobalt) {
        content()
    }
}