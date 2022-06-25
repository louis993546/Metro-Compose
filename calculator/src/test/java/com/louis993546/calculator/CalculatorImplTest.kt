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
    @Test
    fun `click on decimal makes it show up`() = runTest {
        calculator.run {
            decimal()

            assertEquals("0.", display.first().big)
        }
    }

    // TODO click on . when it already exist does nothing
    @Test
    fun `click on decimal again does nothing`() = runTest {
        calculator.run {
            decimal()
            decimal()

            assertEquals("0.", display.first().big)
        }
    }

    // TODO at some point type in a full addition works

    // TODO make sure divide by 0 does not crash the whole thing

    @Test
    fun `max 16 digits`() = runTest {
        calculator.run {
            repeat(17) {
                digit(1)
            }

            assertEquals("1111111111111111", display.first().big)
        }
    }

    @Test
    fun `the 17th char can be decimal`() = runTest {
        calculator.run {
            repeat(17) {
                digit(1)
            }
            decimal()

            assertEquals("1111111111111111.", display.first().big)
        }
    }
}