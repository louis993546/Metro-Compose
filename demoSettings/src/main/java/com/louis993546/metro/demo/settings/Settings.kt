package com.louis993546.metro.demo.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.louis993546.metro.Pages
import com.louis993546.metro.Text
import com.louis993546.metro.TitleBar

@ExperimentalPagerApi
@Composable
fun Settings(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TitleBar(title = "SETTINGS")
        Pages(pageTitles = listOf("system", "applications")) { pageNumber ->
            when (pageNumber) {
                0 -> SystemPage()
                1 -> ApplicationsPage()
            }
        }
    }
}

@Composable
fun SystemPage(
    modifier: Modifier = Modifier,
) {
    Text(text = "TBD", modifier = modifier)
}

@Composable
fun ApplicationsPage(
    modifier: Modifier = Modifier,
) {
    Text(text = "TBD", modifier = modifier)
}
