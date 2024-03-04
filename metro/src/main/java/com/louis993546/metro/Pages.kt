package com.louis993546.metro

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

const val FAKE_PAGE_COUNT = 256

@ExperimentalFoundationApi
@Composable
fun Pages(
    modifier: Modifier = Modifier,
    pageTitles: List<String>,
    content: @Composable BoxScope.(pageNumber: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 12.dp),
    ) {

        val pageCount = pageTitles.size

        val scope = rememberCoroutineScope()
        val scope2 = rememberCoroutineScope()

        val listState = rememberLazyListState(Int.MAX_VALUE / 2)
        val pagerState = rememberPagerState(
            initialPage = (FAKE_PAGE_COUNT * pageTitles.size) / 2,
            initialPageOffsetFraction = 0f,
            pageCount = { FAKE_PAGE_COUNT * pageTitles.size },
        )
        // TODO connect scrollState and pagerState together somehow

        LaunchedEffect(pagerState) {
            var initial = pagerState.currentPage
            var swipeInProgress = false
            var isSwipingBackwards = false

            snapshotFlow { Pair(pagerState.currentPageOffsetFraction, pagerState.currentPage) }.collect { it ->
                // Titles parallax scrolling (eg pivot control)
                var offset = it.first
                val page = it.second

                // Calculate the target at the start of the swipe
                if (!swipeInProgress && abs(offset) > 0.01) {
                    isSwipingBackwards = offset < 0f
                    initial = page
                    swipeInProgress = true
                }

                if (isSwipingBackwards && offset > 0f) {
                    offset -= 1f
                } else if (!isSwipingBackwards && offset < 0f) {
                    offset += 1f
                }
                offset = FastOutSlowInEasing.transform(offset)

                val targetItem = listState.layoutInfo.visibleItemsInfo.firstOrNull {
                    it.key == initial
                } ?: listState.layoutInfo.visibleItemsInfo.lastOrNull()

                if (targetItem == null) {
                    return@collect
                }

                // Calculate the scroll offset based on the swipe offset and the size of the target item
                val scrollOffset = (offset * targetItem.size).roundToInt()

                // Scroll the label into view with the calculated offset
                if (scrollOffset != 0) {
                    listState.scrollToItem(initial, scrollOffset)
                } else {
                    listState.animateScrollToItem(page)
                }

                // Reset swipeInProgress and target when swipe ends
                if (abs(offset) < 0.001) {
                    swipeInProgress = false
                    initial = pagerState.currentPage
                    listState.scrollToItem(page)
                }
            }
        }

        LazyRow(
            state = listState,
            userScrollEnabled = false,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start,
            )
        ) {
            items(
                // See HorizontalPager pageCount comment for more info
                count = FAKE_PAGE_COUNT * pageTitles.size,
                key = { it },
                itemContent = {
                    val index = it % pageCount

                    Text(
                        text = pageTitles[index],
                        size = 52.sp,
                        weight = FontWeight.Light,
                        // Handle duplicates
                        // TODO: Fade in
                        color = if (pagerState.settledPage + pageCount <= it) Color.Transparent
                        else if (pagerState.settledPage % pageCount == index) LocalTextOnBackgroundColor.current
                        else LocalTextOnBackgroundColor.current.copy(alpha = 0.35f),
                        modifier = Modifier.clickable {
                            // 2 scopes to make sure the animations won't wait for one another
                            scope.launch {
                                listState.animateScrollToItem(it)
                            }
                            scope2.launch {
                                pagerState.animateScrollToPage(it)
                            }
                        },
                    )
                }
            )
        }
        HorizontalPager(
            modifier = Modifier.weight(1f),
            state = pagerState,
            contentPadding = PaddingValues(top = 10.dp),
            beyondBoundsPageCount = 3,
            // Ugly hack to support infinite/looping scrolling,
            // officially recommended by @google/accompanist.
        ) { index ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
            ) {
                content(index % pageCount)
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
        text = title.uppercase(),
        size = 18.sp,
        weight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 8.dp),
    )
}
