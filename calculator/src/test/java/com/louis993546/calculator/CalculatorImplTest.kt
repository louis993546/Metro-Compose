package com.louis993546.calculator

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
internal class CalculatorImplTest {
    private val calculator: Calculator
        get() = CalculatorImpl()

    @Test
    fun `starts at 0`() = runTest {
        val value = calculator.display.first()

        assertEquals("0", value)
    }

    @Test
    fun `type 1 digit works`() = runTest {
        (0..9).forEach {
            val value = calculator.run {
                digit(it)

                display.first()
            }

            assertEquals(it.toString(), value)
        }
    }

    @Test
    fun `type 2 digit works`() = runTest {
        (10..99).forEach {
            val value = calculator.run {
                it.toString().forEach { d ->
                    digit(d.digitToInt())
                }

                display.first()
            }

            assertEquals(it.toString(), value)
        }
    }

    // TODO click on operations does NOT change the display

    // TODO click on reset clears the display

    // TODO click on . (TODO check what does the phone do at the moment)

    // TODO at some point type in a full addition works

    // TODO make sure divide by 0 does not crash the whole thing

    // TODO there should be some kind of limit of how many character it supports
}