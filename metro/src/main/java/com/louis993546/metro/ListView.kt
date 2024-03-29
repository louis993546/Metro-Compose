package com.louis993546.metro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


sealed interface ListViewItem {
    data class Header(val contents: @Composable () -> Unit, val label: String, val key: String) : ListViewItem
    data class Content(val contents: @Composable () -> Unit, val label: String, val key: String) : ListViewItem
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListView(
    modifier: Modifier = Modifier,
    items: List<ListViewItem>,
) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        contentPadding = PaddingValues(vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxSize(),
    ) {
        items.forEachIndexed { index, item ->
            when (item) {
                is ListViewItem.Header -> {
                    stickyHeader(key = item.key) {
                        item.contents()
                    }
                }
                is ListViewItem.Content -> {
                    item(key = item.key) {
                        item.contents()
                        Spacer(modifier = Modifier.height(6.dp))

                        // Space between groups like in 8.1 Update 2
                        // TODO Configurable, or even better custom styling
                        if (items.elementAtOrNull(index + 1) is ListViewItem.Header)
                            Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun ListViewHeaderAcronymStyle(
    letter: String
) {
    AcronymIcon(
        modifier = Modifier,
        letter = letter
    )
}

@Composable
fun AcronymIcon(
    modifier: Modifier = Modifier,
    letter: String,
) {
    Box(
        modifier = modifier
            .background(color = LocalBackgroundColor.current)
            .height(62.dp)
            .clickable {  }
    ) {
        Box(
            modifier = Modifier
                .background(color = LocalBackgroundColor.current)
            .size(56.dp)
            .border(width = 3.dp, color = LocalAccentColor.current),
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                .padding(bottom = 4.dp)
                    .align(Alignment.BottomStart),
                text = letter,
                weight = FontWeight.Medium,
                color = LocalAccentColor.current,
                size = 36.sp,
                maxLine = 1
            )
        }
    }
}
