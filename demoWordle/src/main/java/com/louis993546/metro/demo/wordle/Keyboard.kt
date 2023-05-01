package com.louis993546.metro.demo.wordle

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.louis993546.metro.Text

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
//    state: Map<Char, GuessKeyState>,
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
            space = 8.dp, // TODO for smaller screens, replace this with smaller number
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
    @Suppress("MagicNumber")
    val widthBase = (LocalConfiguration.current.screenWidthDp - 16) / 14

    Box(
        modifier = modifier
            .height((widthBase * 2).dp)
            .width((width.factor * widthBase).dp)
            .background(color = Color.Gray),
        content = content,
        contentAlignment = Alignment.Center,
    )
}

sealed class KeyPress {
    data class Action(val action: KeyAction) : KeyPress()
    data class Character(val char: Char) : KeyPress()
}

enum class KeyAction {
    Enter,
    Backspace
}

enum class KeyWidth(val factor: Int) {
    M(1), L(2)
}
