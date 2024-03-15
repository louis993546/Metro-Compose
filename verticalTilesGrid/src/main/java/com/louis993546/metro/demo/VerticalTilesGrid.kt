package com.louis993546.metro.demo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import timber.log.Timber

fun something(width: Int, height: Int) {
    TilesGridScopeInstance.cellList.add(Cell(width, height))
}

interface VerticalTilesGridScope {
    @Composable
    fun Small(content: @Composable () -> Unit)

    @Composable
    fun Medium(content: @Composable () -> Unit)

    @Composable
    fun Wide(content: @Composable () -> Unit)

    @Composable
    fun Large(content: @Composable () -> Unit)
}

class VerticalTilesGridScopeImpl : VerticalTilesGridScope {
    @Composable
    override fun Small(content: @Composable () -> Unit) {
        content()
        something(1, 1)
    }

    @Composable
    override fun Medium(content: @Composable () -> Unit) {
        content()
        something(2, 2)
    }

    @Composable
    override fun Wide(content: @Composable () -> Unit) {
        content()
        something(4, 2)
    }

    @Composable
    override fun Large(content: @Composable () -> Unit) {
        content()
        something(4, 4)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerticalTilesGrid(
    modifier: Modifier = Modifier,
    columns: Int = LocalConfiguration.current.screenWidthDp.toColumnCount(),
    gap: Dp = 8.dp,
    content: @Composable VerticalTilesGridScope.() -> Unit,
) {
    Layout(
        modifier = modifier,
        content = { VerticalTilesGridScopeImpl().content() },
    ) { measurables, constraints ->
        val totalGapWidth = gap.roundToPx() * (columns - 1)
        val cellSize = (constraints.maxWidth - totalGapWidth) / columns

        val maxPossibleRowCount = List(measurables.size) { index ->
            TilesGridScopeInstance.cellList[index].rowCount
        }.sum()
        val matrix = (0 until maxPossibleRowCount).map {
            ((0 until columns).map { -1 }).toMutableList()
        }.toMutableList()

        var currentRow = 0
        var currentColumn = 0

        val placeablesWithCoordinates = measurables.mapIndexed { index, measurable ->
            val whatIsThis = TilesGridScopeInstance.cellList[index]
            val columnCount = whatIsThis.columnCount
            val rowCount = whatIsThis.rowCount

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
                    // this cell is empty, now check if all the necessary cells are empty as well
                    // check if there is enough columns first
                    if ((columns - currentColumn) < columnCount) {
                        currentRow += 1
                        currentColumn = 0
                    } else {
                        // For now, only needs to check cells same row on the right
                        val cellsToCheck =
                            matrix[currentRow].subList(currentColumn, currentColumn + columnCount)
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
            val itemHeight = cellSize * rowCount + gap.roundToPx() * (rowCount - 1)

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
        val tallestLastRowRowCount = placeablesWithCoordinates.filter { it.row == totalRowCount }
            .maxByOrNull { it.heightRowCount }?.heightRowCount ?: 1
        val totalHeight =
            ((totalRowCount + 1) * cellSize) + ((totalRowCount + 1) * gap.roundToPx()) + ((tallestLastRowRowCount - 1) * cellSize)

        layout(
            width = constraints.maxWidth,
            height = totalHeight,
        ) {
            placeablesWithCoordinates.forEach { (placeable, row, column) ->
                Timber.tag("qqqq").d("row = $row, column = $column")
                val x = ((column + 0) * gap.roundToPx()) + (column * cellSize)
                val y = ((row + 0) * gap.roundToPx()) + (row * cellSize)

                Timber.tag("qqqq").d("x = $x, y = $y")

                placeable.placeRelative(
                    x = x,
                    y = y,
                )
            }
        }
    }
}


class Cell(
    val columnCount: Int,
    val rowCount: Int,
)

object TilesGridScopeInstance {
    val cellList = mutableListOf<Cell>()
}

data class PlaceableWithGridCoordinate(
    val placeable: Placeable,
    val row: Int,
    val column: Int,
    val heightRowCount: Int,
)

fun Int.toColumnCount(): Int = if (this > 411) 6 else 4

