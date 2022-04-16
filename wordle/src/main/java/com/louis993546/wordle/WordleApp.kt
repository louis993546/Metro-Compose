package com.louis993546.wordle

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
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.louis993546.metro.Text

@Composable
fun WordleApp(
    modifier: Modifier = Modifier,
) {
    // TODO load some meta data saved on device (e.g. history, color blind mode)
    val guessState by remember { mutableStateOf(WordleGuessState.EMPTY) }
    val keyboardState = remember { mutableStateMapOf<Char, GuessKeyState>() }

    Column(modifier = modifier.fillMaxSize()) {
        GuessGrid(
            modifier = Modifier.fillMaxWidth().weight(1f),
            state = guessState,
        )
        Keyboard(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            state = keyboardState,
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
    Box(
        modifier = modifier
            .size(64.dp) // TODO figure out how to set this dynamically
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

sealed class KeyPress {
    data class Action(val action: KeyAction) : KeyPress()
    data class Character(val char: Char) : KeyPress()
}

enum class KeyAction {
    Enter,
    Backspace
}

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    state: Map<Char, GuessKeyState>,
    onKeyPressed: (KeyPress) -> Unit,
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
                .map { it.uppercase() } // TODO read state
                .forEach {
                    Key(
                        modifier = Modifier
                            .clickable { onKeyPressed(KeyPress.Character(it.first())) }
                    ) { Text(text = it) }
                }
        }

        KeyboardRow {
            "asdfghjkl"
                .map { it.uppercase() } // TODO read state
                .forEach {
                    Key(
                        modifier = Modifier
                            .clickable { onKeyPressed(KeyPress.Character(it.first())) }
                    ) { Text(text = it) }
                }
        }

        KeyboardRow {
            Key(
                modifier = Modifier.clickable { onKeyPressed(KeyPress.Action(KeyAction.Enter)) },
                width = KeyWidth.L,
            ) { Text(text = "Enter") }
            "zxcvbnm"
                .map { it.uppercase() } // TODO read state
                .forEach {
                    Key(
                        modifier = Modifier
                            .clickable { onKeyPressed(KeyPress.Character(it.first())) }
                    ) { Text(text = it) }
                }
            Key(
                modifier = Modifier.clickable { onKeyPressed(KeyPress.Action(KeyAction.Backspace)) },
                width = KeyWidth.L
            ) { Text(text = "←") }
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

enum class GuessKeyState {
//    Unknown, // this is the default
    Wrong,
    WrongPosition,
    Correct
}
