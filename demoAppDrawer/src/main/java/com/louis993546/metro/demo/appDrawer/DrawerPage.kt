package com.louis993546.metro.demo.appDrawer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louis993546.metro.CircleButton
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.LocalTextOnBackgroundColor
import com.louis993546.metro.Text
import com.louis993546.metro.demo.appRow.AppRow
import com.louis993546.metro.demo.apps.Apps

@ExperimentalFoundationApi
@Composable
fun DrawerPage(
    modifier: Modifier = Modifier,
    onAppClick: (Apps) -> Unit,
) {
    val list = appsList
        .sorted()
        .groupBy { it.first().lowercaseChar() }
        .map { (char, list) ->
            val header = ListItem.Header(char.lowercaseChar())
            val items = list.map { ListItem.App(it) }

            listOf(header) + items
        }.flatten()

    val topMargin = 8.dp
    Row(modifier = modifier) {
        SearchButton(modifier = Modifier.padding(all = topMargin)) { onAppClick(Apps.APP_SEARCH) }

        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = topMargin),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            list.forEach { item ->
                when (item) {
                    is ListItem.Header -> {
                        stickyHeader(key = item.char) {
                            Header(
                                modifier = Modifier.fillMaxWidth(),
                                letter = item.char,
                            )
                        }
                    }
                    is ListItem.App -> {
                        item(key = item.name) {
                            AppRow(appName = item.name)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun SearchButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    CircleButton(
        modifier = modifier
            .size(48.dp)
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = "Search",
            colorFilter = ColorFilter.tint(color = LocalTextOnBackgroundColor.current)
        )
    }
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    letter: Char,
) {
    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .background(color = LocalBackgroundColor.current)
                .size(48.dp)
                .border(width = 2.dp, color = LocalAccentColor.current)
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.BottomStart),
                text = letter.toString(),
                size = 24.sp,
            )
        }
    }
}

sealed interface ListItem {
    data class Header(val char: Char) : ListItem
    data class App(val name: String) : ListItem
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
    "Metro Settings",
)
