package com.louis993546.metro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.louis993546.calculator.CalculatorApp
import com.louis993546.metro.ui.theme.MetroDemoTheme

class MainActivity : ComponentActivity() {
    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Phone {
                MetroDemoTheme {
                    Box(modifier = Modifier.background(color = LocalBackgroundColor.current)) {
                        val pagerState = rememberPagerState(pageCount = 2)
                        HorizontalPager(state = pagerState) { page ->
                            when (page) {
                                0 -> HomePage(modifier = Modifier.fillMaxWidth())
                                1 -> DrawerPage(modifier = Modifier.fillMaxWidth())
                                else -> error("WTF")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Phone(content: @Composable () -> Unit) {
    Column{
        Box(modifier = Modifier.aspectRatio(9f / 16f)) {
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
                modifier = Modifier.weight(1f),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_24),
                contentDescription = "back",
                colorFilter = ColorFilter.tint(color = Color.White),
            )
            Image(
                modifier = Modifier.weight(1f),
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

///**
// * TODO icon type?
// */
//sealed interface Tile {
//    val id: String
//
//    /**
//     * 1x1 square
//     */
//    data class Small(override val id: String, val icon: Unit) : Tile
//
//    /**
//     * 2x2 square
//     */
//    data class Medium(override val id: String, val icon: Unit, val text: String) : Tile
//
//    /**
//     * 4x2 rectangle
//     */
//    data class Large(override val id: String, val icon: Unit, val text: String) : Tile
//}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    MetroDemoTheme {
//        Greeting("Android")
//    }
}