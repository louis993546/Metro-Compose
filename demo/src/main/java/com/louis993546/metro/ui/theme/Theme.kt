package com.louis993546.metro.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.louis993546.metro.MetroTheme

@Composable
fun MetroDemoTheme(content: @Composable () -> Unit) {
    MetroTheme(accentColor = Color.LightGray) {
        content()
    }
}