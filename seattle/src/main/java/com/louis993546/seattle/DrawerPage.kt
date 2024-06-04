package com.louis993546.seattle

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.louis993546.metro.ListView
import com.louis993546.metro.ListViewHeaderAcronymStyle
import com.louis993546.metro.ListViewItem
import com.louis993546.metro.Text
import com.louis993546.metro.demo.appRow.AppRow

@ExperimentalFoundationApi
@Composable
fun DrawerPage(
    modifier: Modifier = Modifier,
    apps: List<App>,
) {
    var popupOffset: IntOffset? by remember { mutableStateOf(null) }

    val list = apps.sortedBy { it.label }
        .groupBy { it.label.first().lowercaseChar() }
        .map { (char, list) ->
            val letter = char.lowercase()

            val header = ListViewItem.Header(
                contents = { ListViewHeaderAcronymStyle(letter) },
                label = letter,
                key = letter
            )

            val context = LocalContext.current
            val haptics = LocalHapticFeedback.current

            val items = list.map {
                ListViewItem.Content(
                    contents = {
                        AppRow(
                            name = it.label,
                            icon = it.iconDrawable,
                            modifier = Modifier.combinedClickable(
                                onClick = { context.startActivity(it.intent) },
                                onLongClick = {
                                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                                    // TODO find anchor to show popup
                                    // TODO how do I get everything else to zoom out a bit?
                                    popupOffset = IntOffset(0, 0)
                                },
                            )
                        )
                    },
                    label = it.label,
                    key = it.uuid
                )
            }
            listOf(header) + items
        }
        .flatten()


    // TODO i need to anchor it to the selected option
    if (popupOffset != null) {
        Popup(
            offset = popupOffset!!,
            onDismissRequest = { popupOffset = null }
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(16.dp),
                ) {
                    PopupOption(text = "pin to start")
                    PopupOption(text = "rate and review")
                    PopupOption(text = "uninstall")
                }
            }
        }
    }

    Row(modifier = modifier.padding(horizontal = 8.dp)) {
//        SearchButton(modifier = Modifier.padding(all = topMargin)) { onAppClick(Apps.APP_SEARCH) }

        ListView(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp),
            items = list,
        )
    }
}

// TODO I need a Local "text on popup" color
@Composable
fun PopupOption(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        text = text,
        color = Color.Black,
        modifier = modifier,
        size = 24.sp,
    )
}