package com.louis993546.metro.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.louis993546.calculator.CalculatorApp
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalBackgroundColor
import com.louis993546.metro.LocalTextOnAccentColor
import com.louis993546.metro.Text
import com.louis993546.metro.browser.Browser
import com.louis993546.metro.demo.theme.MetroDemoTheme
import com.louis993546.metro.settings.Settings
import com.louis993546.metro_settings.MetroSettingsApp
import timber.log.Timber

class MainActivity : ComponentActivity() {
    @ExperimentalFoundationApi
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            DeviceFrame(navController = navController) {
                MetroDemoTheme {
                    NavHost(
                        navController = navController,
                        startDestination = "launcher",
                        modifier = Modifier.background(color = LocalBackgroundColor.current)
                    ) {
                        composable(Apps.LAUNCHER.id) { Launcher(navController) }
                        composable(Apps.CALCULATOR.id) { CalculatorApp() }
                        composable(Apps.METRO_SETTINGS.id) { MetroSettingsApp() }
                        composable(Apps.SETTINGS.id) { Settings() }
                        composable(Apps.BROWSER.id) { Browser() }
                        // Add new apps here (step 2)
                    }
                }
            }
        }
    }
}

enum class Apps(val id: String) {
    LAUNCHER("launcher"),
    CALCULATOR("calculator"),
    METRO_SETTINGS("metroSettings"),
    SETTINGS("settings"),
    BROWSER("browser")
    // Add new apps here (step 1)
}

fun NavController.navigate(route: Apps) {
    this.navigate(route.id)
}

@ExperimentalPagerApi
@ExperimentalFoundationApi
@Composable
fun Launcher(
    navController: NavController,
) {
    val pagerState = rememberPagerState(pageCount = 2)
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> HomePage(modifier = Modifier.fillMaxWidth(), navController = navController)
            1 -> DrawerPage(modifier = Modifier.fillMaxWidth(), navController = navController)
            else -> error("WTF")
        }
    }
}

@Composable
fun DeviceFrame(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val ratio =
        LocalConfiguration.current.screenHeightDp.toFloat() / LocalConfiguration.current.screenWidthDp.toFloat()
    val isTallScreen = ratio >= 1.8

    Column {
        Box(
            modifier = Modifier
                // Lumia 920 has 1280 * 768 screen
                .run { if (isTallScreen) this.aspectRatio(9f / 15f) else this }
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

/**
 * Unlike normal Tile, HomeTile can be rectangle, not just square
 */
@Composable
fun HomeTile(
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
    title: String = "",
    backgroundColor: Color = LocalAccentColor.current,
    textColor: Color = LocalTextOnAccentColor.current
) {
    Box(
        modifier = modifier.background(color = backgroundColor)
    ) {
        iconRes?.let { res ->
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center),
                painter = painterResource(id = res),
                contentDescription = "", // TODO fix me
                colorFilter = ColorFilter.tint(textColor),
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 6.dp, bottom = 2.dp),
            text = title,
            color = textColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    MetroDemoTheme {
//        Greeting("Android")
//    }
}