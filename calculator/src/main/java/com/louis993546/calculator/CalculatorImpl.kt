package com.louis993546.calculator

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import kotlinx.parcelize.Parcelize

@Composable
fun rememberCalculator(): Calculator = rememberSaveable(saver = CalculatorImpl.Saver) {
    CalculatorImpl()
}

internal class CalculatorImpl(
    initial: CalculatorSavable? = null,
) : Calculator {
    internal var displayNeedToBeOverrideByNextValue: Boolean by mutableStateOf(
        initial?.displayNeedToBeOverrideByNextValue ?: false
    )

    override var smallDisplay: String? by mutableStateOf(initial?.smallDisplay)
    override var bigDisplay: String by mutableStateOf(initial?.bigDisplay ?: "0")

    private val String.digitCount: Int
        get() = count { it.digitToIntOrNull() != null }

    override fun digit(i: Int) {
        if (bigDisplay.digitCount >= 16) {
            return
        }

        val bigValue = when {
            displayNeedToBeOverrideByNextValue -> {
                displayNeedToBeOverrideByNextValue = false
                i.toString()
            }
            bigDisplay != "0" -> {
                "${bigDisplay}$i"
            }
            else -> {
                i.toString()
            }
        }

        bigDisplay = bigValue
    }

    override fun decimal() {
        if (bigDisplay.contains('.')) {
            // nothing
        } else {
            bigDisplay = "${bigDisplay}."
        }
    }

    override fun operation(op: Calculator.Operation) {
        when (op) {
            Calculator.Operation.C -> {
                smallDisplay = null
                bigDisplay = "0"
            }
            Calculator.Operation.Plus -> plus()
            Calculator.Operation.Backspace -> backspace()
            Calculator.Operation.Equal -> equal()
            Calculator.Operation.Minus -> minus()
            else -> TODO()
        }
    }

    private fun plus() {
        displayNeedToBeOverrideByNextValue = true
        smallDisplay = "${bigDisplay}+"

        // TODO save what is the display representing maybe?
    }

    private fun minus() {
        displayNeedToBeOverrideByNextValue = true
        smallDisplay = "${bigDisplay}-"
    }

    private fun backspace() {
        bigDisplay = bigDisplay.dropLast(1)
            .let { it.ifEmpty { "0" } }
    }

    private fun equal() {
        val formula = "${smallDisplay}${bigDisplay}"

        smallDisplay = null
        bigDisplay = wolframAlpha(formula)
    }

    /**
     * TODO this most likely need to be some kind of convert to AST, and then something process the
     *  AST and figure out all the order and executions
     *  or i can just use this: https://github.com/RotBolt/KParser
     *
     *  TODO currently this only supports int addition. cause if i enable double addition, it will
     *   always add the extra dot zero to end of just int addition
     */
    private fun wolframAlpha(
        formula: String,
    ): String = formula.split('+')
        .sumOf { it.toIntOrNull() ?: wolframAlpha2(formula).toInt() }
        .toString()

    private fun wolframAlpha2(formula: String): String = formula.split('-')
        .map { it.toInt() }
        .reduce { acc, s -> acc - s }
        .toString()

    @Parcelize
    internal data class CalculatorSavable(
        val smallDisplay: String?,
        val bigDisplay: String,
        val displayNeedToBeOverrideByNextValue: Boolean,
    ) : Parcelable

    companion object {
        val Saver: Saver<CalculatorImpl, CalculatorSavable> = Saver(
            save = {
                CalculatorSavable(
                    smallDisplay = it.smallDisplay,
                    bigDisplay = it.bigDisplay,
                    displayNeedToBeOverrideByNextValue = it.displayNeedToBeOverrideByNextValue,
                )
            },
            restore = { CalculatorImpl(it) },
        )
    }
}

