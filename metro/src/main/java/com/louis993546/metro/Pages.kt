package com.louis993546.metro

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun Pages(
    modifier: Modifier = Modifier,
    pageTitles: List<String>,
    page: @Composable BoxScope.(pageNumber: Int) -> Unit,
) {
    Column(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val scope2 = rememberCoroutineScope()

        val listState = rememberLazyListState()
        val pagerState = rememberPagerState(pageCount = pageTitles.size)
        // TODO connect scrollState and pagerState together somehow

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                listState.animateScrollToItem(it)
            }
        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start,
            ),
        ) {
            itemsIndexed(
                items = pageTitles,
                key = { _, item -> item }
            ) { index, title ->
                Text(
                    text = title,
                    size = 48.sp,
                    weight = FontWeight.Light,
                    color = if (pagerState.currentPage == index) LocalTextOnBackgroundColor.current
                    else LocalTextOnBackgroundColor.current.copy(alpha = 0.4f),
                    modifier = Modifier.clickable {
                        // 2 scopes to make sure the animations won't wait for one another
                        scope.launch {
                            listState.animateScrollToItem(index)
                        }
                        scope2.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
        ) { page ->
            Box(modifier = Modifier.fillMaxWidth()) {
                this.page(page)
            }
        }
    }
}