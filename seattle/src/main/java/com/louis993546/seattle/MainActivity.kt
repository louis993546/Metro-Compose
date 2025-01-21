package com.louis993546.seattle

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.LauncherApps
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.louis993546.metro.MetroColor
import com.louis993546.metro.MetroTheme
import timber.log.Timber

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    private val iconPackManager = IconPackManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val installedApps = testingIconPack(getInstalledApps())

        setContent {
            SeattleTheme {
                val pagerState = rememberPagerState(
                    initialPage = 0,
                    initialPageOffsetFraction = 0f,
                    pageCount = { 2 },
                )
                HorizontalPager(state = pagerState) { page ->
                    when (page) {
                        0 -> HomePage(modifier = Modifier.fillMaxSize())
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
        iconPacks.forEach { (t, _) ->
            Timber.tag("qqq").d(t)
        }
        iconPacks["com.whicons.iconpack"]?.run {
            val appsWithBetterIcons = installedApps.map { app ->
                app.copy(
                    iconDrawable = getDrawableIconForPackage(
                        app.info.packageName,
                        app.iconDrawable
                    ),
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
    @Suppress("MagicNumber", "TooGenericExceptionCaught", "SwallowedException")
    private fun getInstalledApps(): List<App> =
        getSystemService(LauncherApps::class.java)
            .getActivityList(null, Process.myUserHandle())
            .map { activityInfo ->
                val icon = try {
                    val drawable =
                        packageManager.getApplicationIcon(activityInfo.applicationInfo.packageName)
                    when {
                        Build.VERSION.SDK_INT >= 26 && drawable is AdaptiveIconDrawable ->
                            drawable.foreground
                        else -> drawable
                    }
                } catch (e: Exception) {
                    val drawable = activityInfo.getIcon(resources.displayMetrics.densityDpi)
                    when {
                        Build.VERSION.SDK_INT >= 33 && drawable is AdaptiveIconDrawable ->
                            drawable.monochrome ?: drawable
                        else -> drawable
                    }
                }

                App(
                    uuid = activityInfo.name,
                    intent = packageManager.getLaunchIntentForPackage(activityInfo.applicationInfo.packageName),
                    label = activityInfo.label.toString(),
                    iconDrawable = icon, // TODO tint to white
                    info = activityInfo.applicationInfo,
                )
            }

    override fun onDestroy() {
        super.onDestroy()
        iconPackManager.setContext(null)
    }
}

data class App(
    val uuid: String,
    val intent: Intent?,
    val label: String,
    val iconDrawable: Drawable,
    val info: ApplicationInfo,
    // TODO things needed to launch the app
    // TODO shortcuts/notifications/etc
)

@Composable
fun SeattleTheme(
    content: @Composable () -> Unit,
) {
    MetroTheme(
        accentColor = MetroColor.cyan,
        content = content,
    )
}
