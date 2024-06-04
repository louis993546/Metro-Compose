package com.louis993546.seattle

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louis993546.metro.Pages
import com.louis993546.metro.Text

@ExperimentalFoundationApi
@Composable
fun LauncherSettings(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Pages(
            content = { i ->
                when (i) {
                    0 -> LauncherSettingsGeneral()
                    1 -> LauncherSettingsHomePage()
                    else -> error("later")
                }
            },
            pageTitles = listOf(
                "General",
                "Home Page"
            )
        )
    }
}

@Composable
fun LauncherSettingsGeneral(
    modifier: Modifier = Modifier,
) {
    // e.g. color
}

@Composable
fun LauncherSettingsHomePage(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(text = "App")
    }
}
