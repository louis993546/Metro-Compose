package com.louis993546.seattle

import android.content.pm.LauncherApps
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.os.Build
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.Text
import com.louis993546.metro.demo.appRow.AppRow
import com.louis993546.metro.forceTapAnimation
import timber.log.Timber

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val iconPackManager = IconPackManager()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val installedApps = testingIconPack(getInstalledApps())

        setContent {
            SeattleTheme {
                val pagerState = rememberPagerState()
                HorizontalPager(
                    state = pagerState,
                    pageCount = 2,
                ) { page ->
                    when (page) {
                        0 -> Text(
                            text = "Home page",
                            modifier = Modifier.fillMaxHeight(),
                        )
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

    private fun testingIconPack(installedApps: List<App>): List<App> {
        iconPackManager.setContext(this)

        val iconPacks = iconPackManager.getAvailableIconPacks(false)
        iconPacks.forEach { (t, _)->
            Timber.tag("qqq").d(t)
        }
        iconPacks["com.whicons.iconpack"]?.run {
            val appsWithBetterIcons = installedApps.map { app ->
                app.copy(
                    iconDrawable = getDrawableIconForPackage(app.packageName, app.iconDrawable),
                )
            }

            return appsWithBetterIcons
        }

        return installedApps
    }

    /**
     * TODO need a way to listen to (or at least pool) app changes
     *
     * @suppress the magic number check as "33" makes more sense then "tiramisu"
     */
    @Suppress("MagicNumber")
    private fun getInstalledApps(): List<App> =
        getSystemService(LauncherApps::class.java)
            .getActivityList(null, Process.myUserHandle())
            .map { activityInfo ->
                val drawable = activityInfo.getIcon(resources.displayMetrics.densityDpi)
                val icon = when {
                    Build.VERSION.SDK_INT >= 33 && drawable is AdaptiveIconDrawable ->
                        drawable.monochrome ?: drawable // TODO tint to white
                    else -> drawable
                }
                App(
                    label = activityInfo.label.toString(),
                    iconDrawable = icon,
                    packageName = activityInfo.applicationInfo.packageName,
                )
            }

    override fun onDestroy() {
        super.onDestroy()
        iconPackManager.setContext(null)
    }
}

data class App(
    val label: String,
    val iconDrawable: Drawable,
    val packageName: String,
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
            val items = list.map { ListItem.Row(it) }

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

                    is ListItem.Row -> {
                        // TODO key should be activity id or something
                        item(key = item.app.label) {
                            AppRow(
                                name = item.app.label,
                                icon = item.app.iconDrawable,
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
    data class Row(val app: App) : ListItem
}


@Composable
fun SeattleTheme(
    content: @Composable () -> Unit,
) {
    MetroTheme(
        accentColor = Color.Black,
        content = content,
    )
}

@Composable
fun Header(
    modifier: Modifier = Modifier,
    letter: Char,
) {
    Box(
        modifier = modifier
            .forceTapAnimation()
    ) {
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
