package com.louis993546.metro.demo

import android.graphics.Camera
import android.graphics.Rect
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.louis993546.app_drawer.DrawerPage
import com.louis993546.app_search.AppSearch
import com.louis993546.apps.Apps
import com.louis993546.calculator.CalculatorApp
import com.louis993546.calendar.Calendar
import com.louis993546.launcher.HomePage
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.browser.Browser
import com.louis993546.metro.demo.theme.MetroDemoTheme
import com.louis993546.metro.settings.Settings
import com.louis993546.metro.wordle.WordleApp
import com.louis993546.metro_settings.MetroSettingsApp
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalPagerApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DeviceFrame(navController = navController) {
                MetroDemoTheme {
                    NavHost(
                        navController = navController,
                        startDestination = Apps.LAUNCHER.id,
                        modifier = Modifier.background(color = LocalBackgroundColor.current)
                    ) {
                        composable(Apps.LAUNCHER) { Launcher(navController) }
                        composable(Apps.CALCULATOR) { CalculatorApp() }
                        composable(Apps.METRO_SETTINGS) { MetroSettingsApp() }
                        composable(Apps.SETTINGS) { Settings() }
                        composable(Apps.BROWSER) { Browser() }
                        composable(Apps.CALENDAR) { Calendar() }
                        composable(Apps.APP_SEARCH) { AppSearch(apps = Apps.values().asList()) }
                        composable(Apps.WORDLE) { WordleApp() }
                        composable(Apps.CAMERA) { Camera() }
                        // Add new apps here (step 2)
                    }
                }
            }
        }
    }
}

private fun NavGraphBuilder.composable(
    apps: Apps,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(route = apps.id, content = content)
}

fun NavController.navigate(route: Apps) {
    this.navigate(route.id)
}

@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun Launcher(
    navController: NavController,
) {
    val scope2 = rememberCoroutineScope()
    val pagerState = rememberPagerState()
    HorizontalPager(
        state = pagerState,
        count = 2,
    ) { page ->
        val navigate: (Apps) -> Unit = { app -> navController.navigate(app) }
        when (page) {
            0 -> HomePage(
                modifier = Modifier.fillMaxWidth(),
                onArrowClick = { scope2.launch { pagerState.animateScrollToPage(1) } },
                onAppClick = navigate,
            )
            1 -> DrawerPage(
                modifier = Modifier.fillMaxWidth(),
                onAppClick = navigate,
            )
            else -> error("WTF")
        }
    }
}

@Composable
fun DeviceFrame(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val isKeyboardOpen by keyboardAsState()

    val ratio =
        LocalConfiguration.current.screenHeightDp.toFloat() / LocalConfiguration.current.screenWidthDp.toFloat()
    val isTallScreen = ratio >= 1.8

    Column {
        // TODO maybe in future I can look into custom overscroll behaviour?
        // CompositionLocalProvider( LocalOverScrollConfiguration provides null ) { }
        Box(
            modifier = Modifier
                .border(color = Color.White, width = 1.dp)
                // Lumia 920 has 1280 * 768 screen
                .run {
                    if (isTallScreen && isKeyboardOpen == Keyboard.Closed) // TODO allow this to be turn off
                        this.aspectRatio(9f / 15f)
                    else
                        this
                }
        ) {
            content()
        }
        Row(
            modifier = Modifier
                .fillMaxHeight()
                .background(color = Color.Black), // like capacitive buttons,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .clickable { navController.popBackStack() },
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "back",
                colorFilter = ColorFilter.tint(color = Color.White),
            )
            Image(
                modifier = Modifier
                    .weight(1f)
                    .clickable {
                        navController.popBackStack(route = Apps.LAUNCHER.id, inclusive = false)
                    },
                painter = painterResource(id = R.drawable.ic_android_black_24dp),
                contentDescription = "home",
                colorFilter = ColorFilter.tint(color = Color.White),
            )
            Image(
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "search",
                colorFilter = ColorFilter.tint(color = Color.White),
            )
        }
    }
}

//region Keyboard State
//https://stackoverflow.com/a/69533584/2384934
enum class Keyboard {
    Opened, Closed
}

@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}
//endregion