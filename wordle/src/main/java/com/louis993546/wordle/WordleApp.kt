package com.louis993546.wordle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louis993546.metro.Text

@Composable
fun WordleApp(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        GuessGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Keyboard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun GuessGrid(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GuessRow()
        GuessRow()
        GuessRow()
        GuessRow()
        GuessRow()
        GuessRow()
    }
}

@Composable
fun GuessRow(
    modifier: Modifier = Modifier,
    // TODO values here
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        "QWERT"
            .map { GuessCellState.WrongPosition(it) }
            .forEach { GuessCell(state = it) }
    }
}

@Composable
fun GuessCell(
    modifier: Modifier = Modifier,
    state: GuessCellState = GuessCellState.Empty,
) {
    Box(
        modifier = modifier
            .size(64.dp) // TODO figure out how to set this dynamically
            .border(
                width = 2.dp,
                color = when (state) {
                    GuessCellState.Empty -> Color.Gray
                    is GuessCellState.Wrong -> Color.Gray
                    else -> Color.Transparent
                }
            )
            .background(
                color = when (state) {
                    is GuessCellState.Correct -> Color.Green
                    is GuessCellState.WrongPosition -> Color.Blue
                    else -> Color.Transparent
                }
            ),
        contentAlignment = Alignment.Center,
    ) {
        when (state) {
            GuessCellState.Empty -> Spacer(modifier = Modifier)
            is GuessCellState.Wrong -> Text(text = state.char.toString())
            is GuessCellState.WrongPosition -> Text(text = state.char.toString())
            is GuessCellState.Correct -> Text(text = state.char.toString())
        }
    }
}

sealed class GuessCellState {
    object Empty : GuessCellState()
    data class Wrong(val char: Char) : GuessCellState()
    data class WrongPosition(val char: Char) : GuessCellState()
    data class Correct(val char: Char) : GuessCellState()
}

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
    ) {
        KeyboardRow {
            "qwertyuiop"
                .map { it.uppercase() }
                .forEach { Key { Text(text = it) } }
        }

        KeyboardRow {
            "asdfghjkl"
                .map { it.uppercase() }
                .forEach { Key { Text(text = it) } }
        }

        KeyboardRow {
            Key(width = KeyWidth.L) { Text(text = "Enter") }
            "zxcvbnm"
                .map { it.uppercase() }
                .forEach { Key { Text(text = it) } }
            Key(width = KeyWidth.L) { Text(text = "←") }
        }
    }
}

@Composable
fun KeyboardRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterHorizontally,
        ),
        content = content,
    )
}

@Composable
fun Key(
    modifier: Modifier = Modifier,
    width: KeyWidth = KeyWidth.M,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .height(56.dp)
            .width((width.factor * 28).dp)
            .background(color = Color.Gray),
        content = content,
        contentAlignment = Alignment.Center,
    )
}

enum class KeyWidth(val factor: Int) {
    M(1), L(2)
}