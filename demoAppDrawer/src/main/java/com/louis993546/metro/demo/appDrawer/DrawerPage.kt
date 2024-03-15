package com.louis993546.metro.demo.appDrawer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.louis993546.metro.CircleButton
import com.louis993546.metro.ListView
import com.louis993546.metro.ListViewHeaderAcronymStyle
import com.louis993546.metro.ListViewItem
import com.louis993546.metro.LocalTextOnBackgroundColor
import com.louis993546.metro.MenuFlyout
import com.louis993546.metro.MenuFlyoutItem
import com.louis993546.metro.demo.appRow.AppRow
import com.louis993546.metro.demo.apps.Apps

@ExperimentalFoundationApi
@Composable
fun DrawerPage(
    modifier: Modifier = Modifier,
    onAppClick: (Apps) -> Unit,
) {
    var actionMenuOpen by remember { mutableStateOf(false) }

    val list = appsList
        .sorted()
        .groupBy { it.first().lowercaseChar() }
        .map { (char, list) ->
            val letter = char.lowercase()
            val header = ListViewItem.Header(
                contents = { ListViewHeaderAcronymStyle(letter) },
                label = letter,
                key = letter
            )

            val haptics = LocalHapticFeedback.current
            val items = list.map {
                ListViewItem.Content(
                    contents = {
                        AppRow(
                            name = it,
                            icon = null,
                            modifier = Modifier.combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    actionMenuOpen = true
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                },
                            )
                        )
                    },
                    label = it,
                    key = it
                )
            }
            listOf(header) + items
        }.flatten()

    Row(
        modifier = modifier
            .padding(horizontal = 6.dp)
    ) {
        SearchButton(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 16.dp),
        ) { onAppClick(Apps.APP_SEARCH) }

        ListView(
            modifier = Modifier
                    .fillMaxSize(),
            items = list,
        )

        MenuFlyout(
            enabled = actionMenuOpen,
            onDismissRequest = { actionMenuOpen = false }
        ) {
            MenuFlyoutItem(text = "Pin to Start")
            MenuFlyoutItem(text = "Share")
            MenuFlyoutItem(text = "Uninstall")
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
            .clickable {
                onClick()
            }
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .scale(scaleX = -1f, scaleY = 1f),
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = "Search",
            colorFilter = ColorFilter.tint(
                    LocalTextOnBackgroundColor.current
            ),
        )
    }
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
