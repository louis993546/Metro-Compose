package com.louis993546.metro_settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import com.louis993546.metro.LocalTextOnBackgroundColor
import com.louis993546.metro.Text
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun MetroSettingsApp(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "METRO SETTINGS",
            size = 20.sp,
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
        )

        val scope = rememberCoroutineScope()

        val listState = rememberLazyListState()
        val pagerState = rememberPagerState(pageCount = 2)
        // TODO connect scrollState and pagerState together somehow

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                listState.animateScrollToItem(it)
            }
        }

        LazyRow(
            modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp),
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start),
        ) {
            itemsIndexed(
                listOf("about", "open-source licenses"),
                key = { i, item -> item }
            ) { index, title ->
                Text(
                    text = title,
                    size = 47.sp,
                    weight = FontWeight.Light,
                    color = if (pagerState.currentPage == index) LocalTextOnBackgroundColor.current
                    else LocalTextOnBackgroundColor.current.copy(alpha = 0.4f),
                    modifier = Modifier.clickable {
                        scope.launch {
                            listState.animateScrollToItem(index)
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> AboutUs(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
                1 -> OpenSourceLicenses(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp))
            }
        }
    }
}

@Composable
fun AboutUs(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Text(text = "Under Construction")
    }
}

@Composable
fun OpenSourceLicenses(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Text(text = "TODO Typography")
        Text(text = "TODO iirc there is some library to scrap all the licenses right?")
    }
}