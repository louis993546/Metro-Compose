package com.louis993546.metro.demo.wordle

import org.junit.Assert.assertEquals
import org.junit.Test

class WordleGameImplTest {
    @Test
    fun `game is empty when start`() {
        val game = WordleGameImpl(null)
        assert(game.guesses.isEmpty())
        assertEquals(WordleStatus.InProgress, game.status)
    }

    @Test
    fun `savable guesses can be restore correctly`() {
        val guesses = listOf("guess".toGuess())
        val savable = WordleGameImpl.WordleGameSavable(
            guesses = guesses,
            status = WordleStatus.InProgress,
        )

        val game = WordleGameImpl(savable)

        assertEquals(guesses , game.guesses)
        assertEquals(WordleStatus.InProgress, game.status)
    }

    @Test
    fun `savable status can be restore correctly`() {
        assert(false) { TODO("not implemented") }
    }

    @Test
    fun `when typing first char, char is visible in guesses`() {
        assert(false) { TODO("not implemented") }
    }

    @Test
    fun `when typing last char, char is visible in guesses`() {
        assert(false) { TODO("not implemented") }
    }

    @Test
    fun `when typing after last char, nothing should change`() {
        assert(false) { TODO("not implemented") }
    }

    @Test
    fun `when delete char pos 0-4, char should be delete`() {
        assert(false) { TODO("not implemented") }
    }

    @Test
    fun `when delete char when current guess is empty, do nothing`() {
        assert(false) { TODO("not implemented") }
    }

    /**
     * TODO Do I need any awy to display state is not complete?
     */
    @Test
    fun `when guessNext before a guess is full, do nothing`() {
        assert(false) { TODO("not implemented") }
    }

}

private fun String.toGuess(state: CharacterState = CharacterState.Tbd) = Guess(
    this.map { c ->
        Character(
            char = c,
            state = state,
        )
    }
)
