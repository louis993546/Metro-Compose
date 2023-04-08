package com.louis993546.seattle

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.louis993546.metro.demo.appRow.AppRow

@ExperimentalFoundationApi
@Composable
fun DrawerPage(
    modifier: Modifier = Modifier,
    apps: List<App>,
) {
    val list = apps.sortedBy { it.label }
        .groupBy { it.label.first().lowercaseChar() }
        .map { (char, list) ->
            val header = ListItem.Header(char.lowercaseChar())
            val items = list.map { ListItem.Row(it) }

            listOf(header) + items
        }
        .flatten()

    val topMargin = 8.dp
    Row(modifier = modifier) {
//        SearchButton(modifier = Modifier.padding(all = topMargin)) { onAppClick(Apps.APP_SEARCH) }

        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = topMargin),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            list.forEach { item ->
                when (item) {
                    is ListItem.Header -> {
                        stickyHeader(key = item.char) {
                            Header(
                                modifier = Modifier.fillMaxWidth(),
                                letter = item.char,
                            )
                        }
                    }

                    is ListItem.Row -> {
                        // TODO key should be activity id or something
                        item(key = item.app.label) {
                            AppRow(
                                appName = item.app.label,
                                appIcon = item.app.iconDrawable,
                            )
                        }
                    }
                }
            }
        }
    }
}
