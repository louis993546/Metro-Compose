package com.louis993546.metro.demo.appSearch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louis993546.metro.ListView
import com.louis993546.metro.ListViewHeaderAcronymStyle
import com.louis993546.metro.ListViewItem
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.TextField
import com.louis993546.metro.demo.appRow.AppRow
import com.louis993546.metro.demo.apps.Apps

@Composable
fun AppSearch(
    modifier: Modifier = Modifier,
    apps: List<Apps>,
) {
    Column(modifier = modifier) {
        var searchQuery by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }

        val list = apps
            .sorted()
            .map { app ->
                ListViewItem.Content(
                    contents = { AppRow(name = app.id, icon = null) },
                    label = app.id,
                    key = app.id
                )
            }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 6.dp)
                .padding(horizontal = 22.dp),
        ) {
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                value = searchQuery,
                onValueChange = { searchQuery = it },
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
            ) {
                ListView(
                    modifier = Modifier
                        .fillMaxSize(),
                    items = list,
                )
            }
        }

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }
    }
}
