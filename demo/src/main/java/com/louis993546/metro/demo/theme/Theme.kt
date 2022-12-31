package com.louis993546.metro.demo.theme

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.louis993546.metro.ExperimentalOverscrollType
import com.louis993546.metro.MetroColor
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.OverscrollType

@ExperimentalOverscrollType
@ExperimentalFoundationApi
@Composable
fun MetroDemoTheme(
    overscanType: OverscrollType,
    content: @Composable () -> Unit,
) {
    MetroTheme(
        accentColor = MetroColor.cyan,
        overscrollType = overscanType,
    ) {
        content()
    }
}

