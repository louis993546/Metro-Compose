package com.louis993546.metro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.louis993546.metro.ui.theme.MetroDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroDemoTheme {
                VerticalTilesGrid2 {
                    Tile(title = "").also { something(1, 1) }
                    Tile(title = "").also { something(1, 1) }
                    Tile(title = "Tile 3").also { something(2, 2) }
                    Tile(title = "").also { something(1, 1) }
                    Tile(title = "Tile 5").also { something(4, 2) }
                    Tile(title = "").also { something(1, 1) }
                    Tile(title = "Tile 7").also { something(2, 2) }
                    Tile(title = "").also { something(1, 1) }
                    Tile(title = "").also { something(1, 1) }
                    Tile(title = "").also { something(1, 1) }
                }
            }
        }
    }
}

@Composable
fun Tile(
    modifier: Modifier = Modifier,
    title: String,
    backgroundColor: Color = LocalAccentColor.current,
    textColor: Color = LocalTextOnAccentColor.current
) {
    Box(
        modifier = modifier.background(color = backgroundColor)
    ) {
        Text(
            modifier = Modifier.align(Alignment.BottomStart).padding(start = 6.dp, bottom = 2.dp),
            text = title,
            color = textColor,
        )
    }
}

fun Int.toColumnCount(): Int = if (this > 360) 6 else 4

interface TilesGridScope {
    fun s(content: @Composable () -> Unit)
    fun m(content: @Composable () -> Unit)
    fun l(content: @Composable () -> Unit)
}

class WhatIsThis(
    val columnCount: Int,
    val rowCount: Int,
//    val content: (index: Int) -> @Composable () -> Unit
)

object TilesGridScopeInstance : TilesGridScope {
    val whatIsThisList = mutableListOf<WhatIsThis>()

    override fun s(content: @Composable () -> Unit) {
        whatIsThisList.add(
            WhatIsThis(
                columnCount = 1,
                rowCount = 1,
//                content = { @Composable { content() } },
            )
        )
    }

    override fun m(content: () -> Unit) {
        whatIsThisList.add(
            WhatIsThis(
                columnCount = 2,
                rowCount = 2,
//                content = { @Composable { content() } },
            )
        )
    }

    override fun l(content: () -> Unit) {
        whatIsThisList.add(
            WhatIsThis(
                columnCount = 4,
                rowCount = 2,
//                content = { @Composable { content() } },
            )
        )
    }

}

data class PlaceableWithGridCoordinate(
    val placeable: Placeable,
    val row: Int,
    val column: Int,
    val heightRowCount: Int,
)

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
    LazyColumn() {

    }
}