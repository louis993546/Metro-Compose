package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerPage(
    modifier: Modifier = Modifier,
) {
    val topMargin = 8.dp
    Row(modifier = modifier) {
        SearchButton(modifier = Modifier.padding(all = topMargin))

        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = topMargin),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            // TODO add header
            items(appsList, key = { it }) { app ->
                Row {
                    AppIcon {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color = LocalAccentColor.current)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        text = app,
                        size = 28.sp,
                    )
                }
            }
        }
    }

}

/**
 * TODO replace me with an icon
 */
@Composable
fun SearchButton(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .size(48.dp)
            .wrapContentSize(Alignment.Center)
            .clip(CircleShape)
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(color = Color.White)
        )
    }
}

@Composable
fun AppIcon(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier.size(48.dp), content = content)
}

// https://youtu.be/2p6zmZQRTzE?t=424
val appsList = listOf(
    "Al Jazeera",
    "Alarms",
    "Amazon Kindle",
    "Amtrak",
    "App Folder",
    "App Social",
    "Archiver",
    "Bank of America",
    "Battery Saver",
    "Calculator",
    "Calendar",
    "Camera",
    "Chase Mobile®",
    "Cortana",
    "Data Sense",
    "eBay",
    "Evernote",
    "Facebook",
    "Fantasia Painter",
    "Finance",
    "FM Radio",
    "Food & Drink",
    "Fotor",
    "Frotz.NET",
    "Games",
    "Google Mail",
    "Groupon",
    "Health & Fitness",
    "HERE Drive+",
    "HERE Maps",
)