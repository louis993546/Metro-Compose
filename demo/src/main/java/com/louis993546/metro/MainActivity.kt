package com.louis993546.metro

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import com.louis993546.metro.ui.theme.MetroDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroDemoTheme {
                VerticalTilesGrid {
                    Tile(title = "Tile 1")
                    Tile(title = "Tile 2")
                    Tile(title = "Tile 3")
                    Tile(title = "Tile 4")
                    Tile(title = "Tile 5")
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
    Log.d("qqqq", backgroundColor.toString())
    Box(
        modifier = modifier
            .background(color = backgroundColor)
            .aspectRatio(1f)
    ) {
        Text(
            modifier = Modifier.align(Alignment.BottomStart),
            text = title,
            color = textColor,
        )
    }
}

@Composable
fun VerticalTilesGrid(
    modifier: Modifier = Modifier,
    columns: Int = 2, // TODO make this four
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val itemWidth = constraints.maxWidth / columns
        // Keep given height constraints, but set an exact width
        val itemConstraints = constraints.copy(
            minWidth = itemWidth,
            maxWidth = itemWidth
        )
        // Measure each item with these constraints
        val placeables = measurables.map { it.measure(itemConstraints) }
        // Track each columns height so we can calculate the overall height
        val columnHeights = Array(columns) { 0 }
        placeables.forEachIndexed { index, placeable ->
            val column = index % columns
            columnHeights[column] += placeable.height
        }
        val height = (columnHeights.maxOrNull() ?: constraints.minHeight)
            .coerceAtMost(constraints.maxHeight)
        layout(
            width = constraints.maxWidth,
            height = height
        ) {
            // Track the Y co-ord per column we have placed up to
            val columnY = Array(columns) { 0 }
            placeables.forEachIndexed { index, placeable ->
                val column = index % columns
                placeable.placeRelative(
                    x = column * itemWidth,
                    y = columnY[column]
                )
                columnY[column] += placeable.height
            }
        }
    }
}

/**
 * TODO icon type?
 */
sealed interface Tile {
    val id: String

    /**
     * 1x1 square
     */
    data class Small(override val id: String, val icon: Unit) : Tile

    /**
     * 2x2 square
     */
    data class Medium(override val id: String, val icon: Unit, val text: String) : Tile

    /**
     * 4x2 rectangle
     */
    data class Large(override val id: String, val icon: Unit, val text: String) : Tile
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
//    MetroDemoTheme {
//        Greeting("Android")
//    }
}