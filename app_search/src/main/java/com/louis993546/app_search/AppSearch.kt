package com.louis993546.app_search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AppSearch(
    modifier: Modifier = Modifier,
    apps: List<App>,
) {

}

@Composable
private fun App(
    modifier: Modifier = Modifier,
    app: String,
) {

}

data class App(
    // TODO icon
    val name: String,
)