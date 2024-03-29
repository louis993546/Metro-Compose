package com.louis993546.metro.demo.theme

import androidx.compose.runtime.Composable
import com.louis993546.metro.MetroColor
import com.louis993546.metro.MetroTheme

@Composable
fun MetroDemoTheme(content: @Composable () -> Unit) {
    MetroTheme(accentColor = MetroColor.cyan) {
        content()
    }
}
