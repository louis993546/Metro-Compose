package com.louis993546.metro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.louis993546.metro.ui.theme.MetroDemoTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroDemoTheme {
                VerticalTilesGrid2 {
                    // TODO figure out how to do this abstract these
                    Tile(title = "Tile 1").also { TilesGridScopeInstance.whatIsThisList.add(WhatIsThis(1, 1)) }
                    Tile(title = "Tile 2").also { TilesGridScopeInstance.whatIsThisList.add(WhatIsThis(1, 1)) }
                    Tile(title = "Tile 3").also { TilesGridScopeInstance.whatIsThisList.add(WhatIsThis(2, 2)) }
                    Tile(title = "Tile 4").also { TilesGridScopeInstance.whatIsThisList.add(WhatIsThis(1, 1)) }
                    Tile(title = "Tile 5").also { TilesGridScopeInstance.whatIsThisList.add(WhatIsThis(4, 2)) }
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
        modifier = modifier.background(color = backgroundColor)) {
        Text(
            modifier = Modifier.align(Alignment.BottomStart),
            text = title,
            color = textColor,
        )
    }
}

private fun Int.toColumnCount(): Int = if (this > 360) 6 else 4

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

@Composable
fun VerticalTilesGrid2(
    modifier: Modifier = Modifier,
    columns: Int = LocalConfiguration.current.screenWidthDp.toColumnCount(),
    gap: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier, // TODO make this scrollable
    ) { measurables, constraints ->
        val totalGapWidth = gap.roundToPx() * (columns + 1)
        val cellSize = (constraints.maxWidth - totalGapWidth) / columns

        val maxPossibleRowCount = measurables
            .mapIndexed { index, _ -> TilesGridScopeInstance.whatIsThisList[index].rowCount }
            .sum()
        val matrix = (0 until maxPossibleRowCount).map {
            ((0 until columns).map { -1 }).toMutableList()
        }.toMutableList()

        val placeablesWithCoordinates = measurables.mapIndexed { index, measurable ->
            val whatIsThis = TilesGridScopeInstance.whatIsThisList[index]
            val columnCount = whatIsThis.columnCount
            val rowCount = whatIsThis.rowCount

            var currentRow = 0
            var currentColumn = 0

            var row: Int? = null
            var column: Int? = null

            while (row == null || column == null) {
                if (matrix[currentRow][currentColumn] != -1) {
                    // this cell is already taken
                    if (currentColumn == (columns - 1)) {
                        currentRow += 1
                        currentColumn = 0
                    } else {
                        currentColumn += 1
                    }
                } else {
                    // this cell is empty, not check if all the necessary cells are empty as well
                    // check if there is enough columns first
                    if ((columns - currentColumn) < columnCount) {
                        currentRow += 1
                        currentColumn = 0
                    } else {
                        // For now, only needs to check cells same row on the right
                        val cellsToCheck = matrix[currentRow].subList(currentColumn, currentColumn + columnCount)
                        if (cellsToCheck.any { it != -1 }) {
                            currentRow += 1
                            currentColumn = 0
                        } else {
                            (currentRow until (currentRow + rowCount)).forEach { r ->
                                (currentColumn until (currentColumn + columnCount)).forEach { c ->
                                    Timber.tag("qqqq").d("[$r][$c]")
                                    matrix[r][c] = index
                                }
                            }
                            Timber.tag("qqqq").d("-----")
                            row = currentRow
                            column = currentColumn
                        }
                    }
                }
            }

            val itemWidth = cellSize * columnCount + gap.roundToPx() * (columnCount - 1)
            val itemHeight = itemWidth / columnCount * whatIsThis.rowCount

            val itemConstraints = constraints.copy(
                minWidth = itemWidth,
                maxWidth = itemWidth,
                minHeight = itemHeight,
                maxHeight = itemHeight,
            )

            PlaceableWithGridCoordinate(
                placeable = measurable.measure(itemConstraints),
                row = row,
                column = column,
                heightRowCount = rowCount,
            )
        }

        val totalRowCount = placeablesWithCoordinates.maxByOrNull { it.row }?.row ?: 0
        val tallestLastRowRowCount = placeablesWithCoordinates.filter { it.row == totalRowCount }.maxByOrNull { it.heightRowCount }?.heightRowCount ?: 1
        val totalHeight = ((totalRowCount + 1) * cellSize) + ((totalRowCount + 2) * gap.roundToPx()) + ((tallestLastRowRowCount - 1) * cellSize)

        layout(
            width = constraints.maxWidth,
            height = totalHeight,
        ) {
            placeablesWithCoordinates.forEach { (placeable, row, column) ->
                val x = ((column + 1) * gap.roundToPx()) + (column * cellSize)
                val y = ((row + 1) * gap.roundToPx()) + (row * cellSize)

                Timber.tag("qqqq").d("x = $x, y = $y")

                placeable.placeRelative(
                    x = x,
                    y = y,
                )
            }
        }
    }
}

data class PlaceableWithGridCoordinate(
    val placeable: Placeable,
    val row: Int,
    val column: Int,
    val heightRowCount: Int,
)

@Composable
fun VerticalTilesGrid(
    modifier: Modifier = Modifier,
    columns: Int = LocalConfiguration.current.screenWidthDp.toColumnCount(),
    gap: Dp = 8.dp,
    content: @Composable () -> Unit
) {
    Layout(
//        content = { TilesGridScopeInstance.content() },
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
    LazyColumn() {
        
    }
}