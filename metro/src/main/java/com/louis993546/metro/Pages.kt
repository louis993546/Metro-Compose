package com.louis993546.metro

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * TODO [pageTitles] should contain each page title
 * TODO it'd be great if [pageTitles] can be some enum
 */
@ExperimentalFoundationApi
@Composable
fun Pages(
    modifier: Modifier = Modifier,
    pageTitles: List<String>,
    page: @Composable BoxScope.(pageNumber: Int) -> Unit,
) {
    Column(modifier = modifier) {
        val pageCount = pageTitles.size

        val scope = rememberCoroutineScope()
        val scope2 = rememberCoroutineScope()

        val listState = rememberLazyListState(Int.MAX_VALUE / 2)
        val pagerState = rememberPagerState(Int.MAX_VALUE / 2)
        // TODO connect scrollState and pagerState together somehow

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                // TODO: Titles parallax scrolling (eg pivot control)
                listState.animateScrollToItem(it)
            }
        }

        LazyRow(
            state = listState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start,
            ),
        ) {
            items(
                // See HorizontalPager pageCount comment for more info
                count = Int.MAX_VALUE,
                itemContent = {
                    val index = it % pageCount

                    Text(
                        text = pageTitles[index],
                        size = 48.sp,
                        weight = FontWeight.Light,
                        color = if (pagerState.currentPage % pageCount == index) LocalTextOnBackgroundColor.current
                        else LocalTextOnBackgroundColor.current.copy(alpha = 0.4f),
                        modifier = Modifier.clickable {
                            // 2 scopes to make sure the animations won't wait for one another
                            scope.launch {
                                listState.animateScrollToItem(it)
                            }
                            scope2.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        }
                    )
                }
            )
        }
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            // Ugly hack to support infinite/looping scrolling,
            // officially recommended by @google/accompanist.
            pageCount = Int.MAX_VALUE,
        ) { index ->
            Box(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
                this.page(index % pageCount)
            }
        }
    }
}

/**
 * Is this an actual component? Or should this be part of [Pages]?
 */
@Composable
fun TitleBar(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        text = title,
        size = 20.sp,
        modifier = modifier.padding(all = 8.dp),
    )
}