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
        val value = calculator.display.first().big

        assertEquals("0", value)
    }

    @Test
    fun `type 1 digit works`() = runTest {
        (0..9).forEach {
            val value = calculator.run {
                digit(it)

                display.first().big
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

                display.first().big
            }

            assertEquals(it.toString(), value)
        }
    }

    @Test
    fun `click on add move the current big display up`() = runTest {
        calculator.run {
            // 35
            digit(3)
            digit(5)

            // +
            operation(Calculator.Operation.Plus)

            assertEquals("35", display.first().big)
            assertEquals("35+", display.first().small)
        }
    }

    @Test
    fun `click on C reset everything`() = runTest {
        calculator.run {
            digit(6)
            assertEquals("6", display.first().big)

            operation(Calculator.Operation.C)
            assertEquals("0", display.first().big)
        }
    }

//    @Test
//    fun `click on MC reset memory only`() = runTest {  }

    // TODO click on . makes it show up the first time

    // TODO click on . when it already exist does nothing

    // TODO at some point type in a full addition works

    // TODO make sure divide by 0 does not crash the whole thing

    // TODO there should be some kind of limit of how many character it supports

//    @Test
//    fun ``() = runTest {  }
}