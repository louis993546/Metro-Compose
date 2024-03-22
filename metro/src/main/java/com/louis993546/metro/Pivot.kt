package com.louis993546.metro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh202919(v=vs.105)

data class PivotItem(val header: String, val contents: @Composable () -> Unit)

@ExperimentalFoundationApi
@Composable
fun Pivot(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    items: List<PivotItem>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .then(modifier),
    ) {
        val headersState = rememberLazyListState(
            initialFirstVisibleItemIndex = items.size,
        )
        val state = rememberPagerState(
            pageCount = { items.size * 3 },
            initialPage = items.size,
        )

        // Pivot Headers
        LazyRow(
            state = headersState,
            contentPadding = contentPadding.apply { PaddingValues(top = 10.dp) },
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start,
            ),
            modifier = Modifier
                .padding(bottom = 12.dp)
                .fillMaxWidth(),
        ) {
            itemsIndexed(items = items + items + items) { index, item ->
                val active = index == state.settledPage
                val offset = state.currentOffsetForPage(index)

                // Pivot Header
                Text(
                    text = item.header.lowercase(),
                    size = 48.sp,
                    weight = FontWeight.Light,
                    color = LocalTextOnBackgroundColor.current.copy(alpha = if (active) 1f else 0.5f),
                    modifier = Modifier
                        .padding(contentPadding)
                )
            }
        }

        HorizontalPager(
            state = state,
            contentPadding = contentPadding.apply { PaddingValues(top = 10.dp) },
            beyondBoundsPageCount = 3,
            modifier = Modifier
                .weight(1f),
        ) { index ->
            val item = items[index % items.size]

            // Pivot Item Control
            Box(
                modifier = Modifier
                    .padding(contentPadding)
                    .fillMaxSize()
            ) {
                item.contents()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun PagerState.currentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
