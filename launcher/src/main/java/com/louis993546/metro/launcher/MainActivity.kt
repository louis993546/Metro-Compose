package com.louis993546.metro.launcher

import android.content.pm.LauncherApps
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.Text
import com.louis993546.metro.demo.appRow.AppRow

@ExperimentalFoundationApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val installedApps = getInstalledApps()

        setContent {
            MetroLauncherTheme {
                val pagerState = rememberPagerState()
                HorizontalPager(
                    state = pagerState,
                    count = 2,
                ) { page ->
                    when (page) {
                        0 -> Text(text = "Home page")
                        1 -> DrawerPage(
                            modifier = Modifier.fillMaxWidth(),
                            apps = installedApps,
                        )
                        else -> error("WTF why am I getting $page")
                    }
                }
            }
        }
    }

    /**
     * TODO need a way to listen to (or at least pool) app changes
     */
    private fun getInstalledApps(): List<App> =
        getSystemService(LauncherApps::class.java)
            .getActivityList(null, Process.myUserHandle())
            .map {
                App(
                    label = it.label.toString(),
                    iconDrawable = it.getIcon(resources.displayMetrics.densityDpi),
                )
            }
}

data class App(
    val label: String,
    val iconDrawable: Drawable,
    // TODO things needed to launch the app
    // TODO shortcuts/notifications/etc
)

@ExperimentalFoundationApi
@Composable
fun DrawerPage(
    modifier: Modifier = Modifier,
    apps: List<App>,
) {
    val list = apps.sortedBy { it.label }
        .groupBy { it.label.first().lowercaseChar() }
        .map { (char, list) ->
            val header = ListItem.Header(char.lowercaseChar())
            val items = list.map { ListItem.App(it) }

            listOf(header) + items
        }
        .flatten()

    val topMargin = 8.dp
    Row(modifier = modifier) {
//        SearchButton(modifier = Modifier.padding(all = topMargin)) { onAppClick(Apps.APP_SEARCH) }

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
                        item(key = item.app.label) { // TODO key should be activity id or something
                            AppRow(
                                appName = item.app.label,
                                appIcon = item.app.iconDrawable,
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed interface ListItem {
    data class Header(val char: Char) : ListItem
    data class App(val app: com.louis993546.metro.launcher.App) : ListItem
}


@Composable
fun MetroLauncherTheme(
    content: @Composable () -> Unit,
) {
    MetroTheme(
        accentColor = Color.Cyan,
        content = content,
    )
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
