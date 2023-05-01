package com.louis993546.metro.demo.appSearch

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.louis993546.metro.TextField
import com.louis993546.metro.demo.appRow.AppRow
import com.louis993546.metro.demo.apps.Apps

@Composable
fun AppSearch(
    modifier: Modifier = Modifier,
    apps: List<Apps>,
) {
    var searchQuery by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
    ) {
        TextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .padding(8.dp)
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
            value = searchQuery,
            onValueChange = { searchQuery = it },
        )
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(apps, { it.id }) {
                AppRow(name = it.name, icon = null)
            }
        }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}
