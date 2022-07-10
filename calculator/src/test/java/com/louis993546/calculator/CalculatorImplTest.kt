package com.louis993546.calculator

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

internal class CalculatorImplTest {
    private val calculator: Calculator
        get() = CalculatorImpl()

    @Test
    fun `starts at 0`() {
        val value = calculator.bigDisplay

        assertEquals("0", value)
    }

    @Test
    fun `type 1 digit works`() {
        (0..9).forEach {
            val value = calculator.run {
                digit(it)

                bigDisplay
            }

            assertEquals(it.toString(), value)
        }
    }

    @Test
    fun `type 2 digit works`() {
        (10..99).forEach {
            val value = calculator.run {
                it.toString().forEach { d ->
                    digit(d.digitToInt())
                }

                bigDisplay
            }

            assertEquals(it.toString(), value)
        }
    }

    @Test
    fun `click on add move the current big display up`() {
        calculator.run {
            // 35
            digit(3)
            digit(5)

            // +
            operation(Calculator.Operation.Plus)

            assertEquals("35", bigDisplay)
            assertEquals("35+", smallDisplay)
        }
    }

    @Test
    fun `click on C reset everything`() {
        calculator.run {
            digit(6)
            assertEquals("6", bigDisplay)

            operation(Calculator.Operation.C)
            assertEquals("0", bigDisplay)
        }
    }

//    @Test
//    fun `click on MC reset memory only`() {  }

    @Test
    fun `click on decimal makes it show up`() {
        calculator.run {
            decimal()

            assertEquals("0.", bigDisplay)
        }
    }

    @Test
    fun `click on decimal again does nothing`() {
        calculator.run {
            decimal()
            decimal()

            assertEquals("0.", bigDisplay)
        }

        calculator.run {
            digit(1)
            decimal()
            digit(2)
            decimal()

            assertEquals("1.2", bigDisplay)
        }
    }

    // TODO at some point type in a full addition works
    @Test
    fun `one plus one works`() {
        calculator.run {
            digit(1)
            operation(Calculator.Operation.Plus)
            digit(1)
            operation(Calculator.Operation.Equal)

            assertEquals("2", bigDisplay)
            assertNull(smallDisplay)
        }
    }

    // TODO make sure divide by 0 does not crash the whole thing

    @Test
    fun `max 16 digits`() {
        calculator.run {
            repeat(17) {
                digit(1)
            }

            assertEquals("1111111111111111", bigDisplay)
        }
    }

    @Test
    fun `the 17th char can be decimal`() {
        calculator.run {
            repeat(17) {
                digit(1)
            }
            decimal()

            assertEquals("1111111111111111.", bigDisplay)
        }
    }

    @Test
    fun `backspace removes previous char`() {
        calculator.run {
            digit(1)
            digit(2)
            digit(3)

            operation(Calculator.Operation.Backspace)

            assertEquals("12", bigDisplay)
        }
    }

    @Test
    fun `backspace on last digit turns it into 0`() {
        calculator.run {
            digit(1)
            digit(2)
            digit(3)

            operation(Calculator.Operation.Backspace)
            operation(Calculator.Operation.Backspace)
            operation(Calculator.Operation.Backspace)

            assertEquals("0", bigDisplay)
        }
    }

    @Test
    fun `backspace too many times and it will still be 0`() {
        calculator.run {
            digit(1)
            digit(2)
            digit(3)

            operation(Calculator.Operation.Backspace)
            operation(Calculator.Operation.Backspace)
            operation(Calculator.Operation.Backspace)
            operation(Calculator.Operation.Backspace)
            operation(Calculator.Operation.Backspace)
            operation(Calculator.Operation.Backspace)

            assertEquals("0", bigDisplay)
        }
    }
}