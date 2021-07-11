package com.louis993546.metro_settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.louis993546.metro.ApplicationBar
import com.louis993546.metro.LocalTextOnBackgroundColor
import com.louis993546.metro.LocalTextOnButtonColor
import com.louis993546.metro.Text
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * TODO extract these into metro lib
 */
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
        val scope2 = rememberCoroutineScope()

        val listState = rememberLazyListState()
        val pagerState = rememberPagerState(pageCount = 2)
        // TODO connect scrollState and pagerState together somehow

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                listState.animateScrollToItem(it)
            }
        }

        LazyRow(
            modifier = Modifier.padding(top = 8.dp),
            state = listState,
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Start),
        ) {
            itemsIndexed(
                listOf("about", "open-source licenses"),
                key = { _, item -> item }
            ) { index, title ->
                Text(
                    text = title,
                    size = 47.sp,
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
            when (page) {
                0 -> AboutUs(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                1 -> OpenSourceLicenses(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }
        ApplicationBar(
            modifier = Modifier.fillMaxWidth(),
            count = 1,
            icon = {
                Image(
                    // TODO this icon looks terrible in center of the circle
                    painter = painterResource(id = R.drawable.ic_baseline_share_24),
                    contentDescription = "Share",
                    colorFilter = ColorFilter.tint(color = LocalTextOnButtonColor.current)
                )
            }
        ) { TODO("on click") }
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