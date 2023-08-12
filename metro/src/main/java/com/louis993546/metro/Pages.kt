package com.louis993546.metro

import android.util.Log
import androidx.compose.animation.*
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
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * TODO [pageTitles] should contain each page title
 * TODO it'd be great if [pageTitles] can be some enum
 */
@OptIn(ExperimentalAnimationApi::class)
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
            initialPage = Int.MAX_VALUE / 2,
            initialPageOffsetFraction = 0f,
            pageCount = Int.Companion::MAX_VALUE,
        )
        // TODO connect scrollState and pagerState together somehow

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.isScrollInProgress }.filter { it }.collect {
                listState.scrollToItem(pagerState.currentPage)
            }
        }
        LaunchedEffect(pagerState) {
            snapshotFlow {
                Pair (
                    pagerState.isScrollInProgress,
                    pagerState.currentPageOffsetFraction
                )
            }.filter { it.first }.collect { it ->
                // Titles parallax scrolling (eg pivot control)
                // FIXME: This is probably extremely inefficient.
                val page = pagerState.settledPage
                var target = page + 1
                var item = listState.layoutInfo.visibleItemsInfo.firstOrNull() { it.key == page }

                // Swiping backwards
                var backwards = false
                if (pagerState.settledPage > pagerState.targetPage || pagerState.settledPage > pagerState.currentPage) {
                    backwards = true
                    target = page - 1
                    item = listState.layoutInfo.visibleItemsInfo.firstOrNull() { it.index % pageCount == (page - 1) % pageCount }
                    if (item == null)
                        item = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                }

                if (item == null) {
                    // FIXME
                    Log.w("GUI", "Couldn't get label")
                    return@collect
                }

                var offset = it.second
                if (it.second < 0f && !backwards)
                    offset += 1.05f
                else if (it.second > 0f && backwards)
                    offset -= 1.05f

                // Easing
                offset = FastOutSlowInEasing.transform(offset)

                // Finally scroll the label into view with the offset we created
                val res = (offset * item.size).roundToInt()
                listState.scrollToItem(if (res != 0 || pagerState.currentPage == page) page else target, res)
            }
        }
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.settledPage }.collect {
                listState.animateScrollToItem(it)
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
                count = Int.MAX_VALUE,
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
        text = title,
        size = 18.sp,
        weight = FontWeight.Bold,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 8.dp),
    )
}
