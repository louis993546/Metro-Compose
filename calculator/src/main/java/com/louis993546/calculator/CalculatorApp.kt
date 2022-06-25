package com.louis993546.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalButtonColor
import com.louis993546.metro.LocalTextOnAccentColor
import com.louis993546.metro.LocalTextOnButtonColor
import com.louis993546.metro.Text
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

/*
 * TODO hack. How can i "remember" an instance of calculator? Like this would break if I want to
 *  have multiple instances of calculator
 */
private val calculator: Calculator = CalculatorImpl()

@Composable
fun CalculatorApp(
    modifier: Modifier = Modifier,
) {
    val display by calculator.display.collectAsState(initial = Display.empty)

    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.align(Alignment.BottomEnd),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End,
            ) {
                display.small?.run {
                    Text(text = this, size = 16.sp)
                }
                Text(text = display.big, size = 64.sp)
            }
        }


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "C") {
                calculator.operation(Calculator.Operation.C)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "MC") {
                TODO()
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "MR") {
                TODO()
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "M+") {
                TODO()
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "⌫") {
                TODO()
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "±") {
                TODO()
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "%") {
                TODO()
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "÷") {
                TODO()
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "7") {
                calculator.digit(7)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "8") {
                calculator.digit(8)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "9") {
                calculator.digit(9)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "×") {
                TODO()
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "4") {
                calculator.digit(4)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "5") {
                calculator.digit(5)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "6") {
                calculator.digit(6)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "-") {
                TODO()
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "1") {
                calculator.digit(1)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "2") {
                calculator.digit(2)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "3") {
                calculator.digit(3)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = "+") {
                calculator.operation(Calculator.Operation.Plus)
            }
        }

        // TODO see if this is fixable, if not, make my own layout again i guess 🤷
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(2.1f), text = "0") {
                calculator.digit(0)
            }
            CalculatorButton(modifier = Modifier.weight(1f), text = ".") {
                calculator.decimal()
            }
            CalculatorButton(
                modifier = Modifier.weight(1f),
                backgroundColor = LocalAccentColor.current,
                textColor = LocalTextOnAccentColor.current,
                text = "=",
            ) {
                TODO()
            }
        }
    }
}


@Composable
internal fun CalculatorButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalButtonColor.current,
    textColor: Color = LocalTextOnButtonColor.current,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .heightIn(min = 64.dp)
            .background(color = backgroundColor)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            size = 24.sp,
            color = textColor,
        )
    }
}

internal interface Calculator {
    val display: Flow<Display>

    fun digit(i: Int)

    fun decimal()

    fun operation(op: Operation)

    enum class Operation {
        Plus, Minus, Multiplier, Divide, Equal,
        // TODO should these all just be operation? or should i split it out to sth else?
        C, MC, MR, Mplus, Backspace
    }
}

data class Display(
    val big: String,
    val small: String?,
) {
    companion object {
        val empty = Display(big = "0", small = null)
    }
}

internal class CalculatorImpl : Calculator {
    // TODO it needs to have something observable as a state of the display
    private val _display = MutableStateFlow(Display.empty)
    override val display: Flow<Display> = _display

    private var displayNeedToBeOverrideByNextValue = false

    override fun digit(i: Int) {
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
        TODO("Not yet implemented")
    }

    override fun operation(op: Calculator.Operation) {
        when (op) {
            Calculator.Operation.C -> { _display.value = Display.empty }
            Calculator.Operation.Plus -> {
                displayNeedToBeOverrideByNextValue = true
                _display.value = _display.value.copy(
                    small = "${_display.value.big}+"
                )
            }
            else -> TODO()
        }
    }
}