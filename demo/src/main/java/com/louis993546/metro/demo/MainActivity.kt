package com.louis993546.metro.demo

import android.graphics.Camera
import android.graphics.Rect
import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.demo.appDrawer.DrawerPage
import com.louis993546.metro.demo.appSearch.AppSearch
import com.louis993546.metro.demo.apps.Apps
import com.louis993546.metro.demo.browser.Browser
import com.louis993546.metro.demo.calculator.CalculatorApp
import com.louis993546.metro.demo.calendar.Calendar
import com.louis993546.metro.demo.launcher.HomePage
import com.louis993546.metro.demo.metroSettings.MetroSettingsApp
import com.louis993546.metro.demo.metroSettings.MetroSettingsConfiguration
import com.louis993546.metro.demo.metroSettings.MetroSettingsDataSource
import com.louis993546.metro.demo.metroSettings.metroSettingsDataSource
import com.louis993546.metro.demo.settings.Settings
import com.louis993546.metro.demo.theme.MetroDemoTheme
import com.louis993546.metro.demo.wordle.WordleApp
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val metroSettings = LocalContext.current.metroSettingsDataSource

            DeviceFrame(
                navController = navController,
                metroSettingsDataSource = metroSettings,
            ) {
                MetroDemoTheme {
                    NavHost(
                        navController = navController,
                        startDestination = Apps.LAUNCHER.id,
                        modifier = Modifier.background(color = LocalBackgroundColor.current)
                    ) {
                        composable(Apps.LAUNCHER) { Launcher(navController) }
                        composable(Apps.CALCULATOR) { CalculatorApp() }
                        composable(Apps.METRO_SETTINGS) { MetroSettingsApp(dataSource = metroSettings) }
                        composable(Apps.SETTINGS) { Settings() }
                        composable(Apps.BROWSER) { Browser() }
                        composable(Apps.CALENDAR) { Calendar() }
                        composable(Apps.APP_SEARCH) { AppSearch(apps = Apps.entries) }
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
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(route = apps.id, content = content)
}

fun NavController.navigate(route: Apps) {
    this.navigate(route.id)
}

@ExperimentalFoundationApi
@Composable
fun Launcher(
    navController: NavController,
) {
    val scope2 = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { 2 },
    )
    HorizontalPager(state = pagerState) { page ->
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
    modifier: Modifier = Modifier,
    navController: NavHostController,
    metroSettingsDataSource: MetroSettingsDataSource,
    content: @Composable () -> Unit,
) {
    val isKeyboardOpen by keyboardAsState()
    val configuration by metroSettingsDataSource.getConfiguration()
        .collectAsState(initial = MetroSettingsConfiguration.INITIAL)

    val ratio =
        LocalConfiguration.current.screenHeightDp.toFloat() / LocalConfiguration.current.screenWidthDp.toFloat()
    val isTallScreen = ratio >= configuration.isTallScreenRatio

    Column(modifier = modifier) {
        // TODO maybe in future I can look into custom overscroll behaviour?
        // CompositionLocalProvider( LocalOverScrollConfiguration provides null ) { }
        Box(
            modifier = Modifier
                .run {
                    if (isTallScreen && isKeyboardOpen == Keyboard.Closed) // TODO allow this to be turn off
                        this.aspectRatio(
                            configuration.frameRatio
                                ?: error("Maybe I need to define a better type")
                        )
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
fun keyboardAsState(
    bufferPercentage: Float = 0.15f,
): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * bufferPercentage) {
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
