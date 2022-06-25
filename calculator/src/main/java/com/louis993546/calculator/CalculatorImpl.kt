package com.louis993546.calculator

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.regex.Pattern

internal class CalculatorImpl : Calculator {
    private val _display = MutableStateFlow(Display.empty)
    override val display: Flow<Display> = _display

    private var displayNeedToBeOverrideByNextValue = false

    private val String.digitCount: Int
        get() = count { it.digitToIntOrNull() != null }

    override fun digit(i: Int) {
        if (_display.value.big.digitCount >= 16) {
            return
        }

        val bigValue = when {
            displayNeedToBeOverrideByNextValue -> {
                displayNeedToBeOverrideByNextValue = false
                i.toString()
            }
            _display.value.big != "0" -> { "${_display.value.big}$i" }
            else -> { i.toString() }
        }

        _display.value = _display.value.copy(big = bigValue)
    }

    override fun decimal() {
        if (_display.value.big.contains('.')) {
            // nothing
        } else {
            _display.value = _display.value.copy(
                big = "${_display.value.big}."
            )
        }
    }

    override fun operation(op: Calculator.Operation) {
        when (op) {
            Calculator.Operation.C -> { _display.value = Display.empty }
            Calculator.Operation.Plus -> plus()
            Calculator.Operation.Backspace -> backspace()
            Calculator.Operation.Equal -> equal()
            else -> TODO()
        }
    }

    private fun plus() {
        displayNeedToBeOverrideByNextValue = true
        _display.value = _display.value.copy(
            small = "${_display.value.big}+"
        )

        // TODO save what is the display representing maybe?
    }

    private fun backspace() {
        val newValue = _display.value.big.dropLast(1)
            .let { it.ifEmpty { "0" } }

        _display.value = _display.value.copy(big = newValue)
    }

    private fun equal() {
        val formula = "${_display.value.small}${_display.value.big}"

        _display.value = Display(
            big = wolframAlpha(formula),
            small = null
        )
    }

    private fun wolframAlpha(formula: String): String {
        return formula.split('+').sumOf { it.toInt() }.toString()
    }
}
