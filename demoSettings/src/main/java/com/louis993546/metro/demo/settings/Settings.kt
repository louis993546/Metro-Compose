package com.louis993546.metro.demo.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louis993546.metro.Pivot
import com.louis993546.metro.PivotItem
import com.louis993546.metro.Text
import com.louis993546.metro.TitleBar

@ExperimentalFoundationApi
@Composable
fun Settings(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TitleBar(title = "Settings")
        Pivot(
            items = listOf(
                PivotItem("System") {
                    SystemPage()
                },
                PivotItem("Applications") {
                    ApplicationsPage()
                },
            ),
        )
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
