package com.louis993546.metro.demo.wordle

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import kotlinx.parcelize.Parcelize

/**
 * An instance of wordle game
 */
interface WordleGame {
    val guesses: List<Guess>

    val status: WordleStatus

    fun type(char: Char)

    fun deleteLastChar()

    fun guessNext()
}

@Parcelize
data class Guess(
    val character: List<Character>,
) : Parcelable

@Parcelize
data class Character(
    val char: Char,
    val state: CharacterState = CharacterState.Tbd,
) : Parcelable

enum class CharacterState {
    Correct,
    WrongPosition,
    Wrong,
    Tbd,
}

enum class WordleStatus {
    Win,
    GameOver,
    InProgress
}

@Composable
fun rememberWordleGame(): WordleGame = rememberSaveable(saver = WordleGameImpl.Saver) {
    WordleGameImpl()
}

internal class WordleGameImpl(
    initial: WordleGameSavable? = null,
) : WordleGame {
    override val guesses: List<Guess> = initial?.guesses.orEmpty().toMutableStateList()

    override val status: WordleStatus by mutableStateOf(
        initial?.status ?: WordleStatus.InProgress
    )

    override fun type(char: Char) {
        TODO("Not yet implemented")
    }

    override fun guessNext() {
        TODO("Not yet implemented")
    }

    override fun deleteLastChar() {
        TODO("Not yet implemented")
    }

    @Parcelize
    data class WordleGameSavable(
        val guesses: List<Guess>,
        val status: WordleStatus
    ) : Parcelable

    companion object {
        val Saver: Saver<WordleGameImpl, WordleGameSavable> = Saver(
            save = {
                WordleGameSavable(
                    guesses = it.guesses,
                    status = it.status,
                )
            },
            restore = { WordleGameImpl(it) }
        )
    }
}
