package com.louis993546.app_search

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louis993546.app_row.AppRow
import com.louis993546.apps.Apps

@Composable
fun AppSearch(
    modifier: Modifier = Modifier,
    apps: List<Apps>,
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(apps, { it.id }) {
            AppRow(appName = it.name)
        }
    }
}
