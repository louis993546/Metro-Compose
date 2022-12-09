package com.louis993546.metro.launcher

import android.content.pm.LauncherApps
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.Text
import com.louis993546.metro.demo.appRow.AppRow

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
    private fun getInstalledApps(): List<String> =
        getSystemService(LauncherApps::class.java)
            .getActivityList(null, Process.myUserHandle())
            .map { it.label.toString() }
            .sorted()
}

@Composable
fun DrawerPage(
    modifier: Modifier = Modifier,
    apps: List<String>,
) {
    val topMargin = 8.dp
    Row(modifier = modifier) {
//        SearchButton(modifier = Modifier.padding(all = topMargin)) { onAppClick(Apps.APP_SEARCH) }

        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(vertical = topMargin),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            apps.forEach { item ->
//                when (item) {
//                    is ListItem.Header -> {
//                        stickyHeader(key = item.char) {
//                            Header(
//                                modifier = Modifier.fillMaxWidth(),
//                                letter = item.char,
//                            )
//                        }
//                    }
//                    is ListItem.App -> {
//                item(key = item.name) {
                        item(key = item) {
                            AppRow(appName = item)
                        }
//                    }
//                }
            }
        }
    }
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
