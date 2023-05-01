
package com.louis993546.metro.demo.wordle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.louis993546.metro.Text

@Composable
fun WordleApp(
    modifier: Modifier = Modifier,
) {
    // TODO load some meta data saved on device (e.g. history, color blind mode)
    val guessState by remember { mutableStateOf(WordleGuessState.EMPTY) }
//    val keyboardState = remember { mutableStateMapOf<Char, GuessKeyState>() }

    Column(modifier = modifier.fillMaxSize()) {
        GuessGrid(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = guessState,
        )
        Keyboard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
//            state = keyboardState,
            onKeyPressed = {
                when (it) {
                    is KeyPress.Action -> TODO()
                    is KeyPress.Character -> TODO()
                }
            }
        )
        // TODO extra action buttons in Metro-style bottom bar
    }
}

@Composable
fun GuessGrid(
    modifier: Modifier = Modifier,
    state: WordleGuessState,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) { state.guesses.forEach { GuessRow(state = it) } }
}

@Composable
fun GuessRow(
    modifier: Modifier = Modifier,
    state: GuessRowState,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) { state.cells.forEach { GuessCell(state = it) } }
}

@Composable
fun GuessCell(
    modifier: Modifier = Modifier,
    state: GuessCellState = GuessCellState.Empty,
) {
    @Suppress("MagicNumber")
    val cellWidth = LocalConfiguration.current.screenWidthDp / 6
    // TODO figure out how to get parent size, and then min it with width

    Box(
        modifier = modifier
            .size(cellWidth.dp)
            .border(
                width = 2.dp,
                color = when (state) {
                    GuessCellState.Empty -> Color.Gray
                    is GuessCellState.Wrong -> Color.Gray
                    is GuessCellState.Guessing -> Color.Gray
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
            is GuessCellState.Guessing -> Text(text = state.char.toString())
        }
    }
}

sealed class GuessCellState {
    object Empty : GuessCellState()
    data class Guessing(val char: Char) : GuessCellState()
    data class Wrong(val char: Char) : GuessCellState()
    data class WrongPosition(val char: Char) : GuessCellState()
    data class Correct(val char: Char) : GuessCellState()
}

data class WordleGuessState(
    val guesses: List<GuessRowState>,
) {
    companion object {
        val EMPTY = WordleGuessState(MutableList(6) { GuessRowState.EMPTY })
    }
}

data class GuessRowState(
    val cells: List<GuessCellState>,
) {
    companion object {
        val EMPTY = GuessRowState(MutableList(5) { GuessCellState.Empty })
    }
}
