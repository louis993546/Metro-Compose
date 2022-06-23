package com.louis993546.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun CalculatorApp(
    modifier: Modifier = Modifier,
) {
//    val items = remember { mutableStateListOf<Option>(Option.Number.Integer(0)) }
    var text by remember { mutableStateOf("0") }

//    fun something(newOption: Option) {
//        val lastOption = items.last()
//        val (prev, new) = when {
//            (lastOption is Option.Number.Floating && newOption is Option.Number.Floating) ||
//                    (lastOption is Option.Operation && newOption is Option.Operation)
//            -> lastOption to null
//
//            (lastOption is Option.Operation) || (newOption is Option.Operation)
//            -> lastOption to newOption
//            (lastOption is Option.Number.Integer && newOption is Option.Number.Integer) -> {
//                val lastInt = lastOption.int
//                val newInt = newOption.int
//                Option.Number.Integer(lastInt + newInt) to null
//            }
//            (lastOption is Option.Number.Integer && newOption is Option.Number.Floating) -> {
//                Option.Number.Floating(lastOption.int.toFloat() + newOption.float) to null
//            }
//            (lastOption is Option.Number.Floating && newOption is Option.Number.Integer) -> {
//                Option.Number.Floating(lastOption.float + newOption.int.toFloat()) to null
//            }
//            else -> error("WTF")
//        }
//        items[items.lastIndex] = prev
//        new?.let { items.add(it) }
//
//        text = items[items.lastIndex].toString()
//    }
//
//    fun calculate() {
//        val ints = items.filterIsInstance<Option.Number.Integer>().map { it.int }
//        val floats = items.filterIsInstance<Option.Number.Floating>().map { it.float }
//
//        val sum = ints.sum() + floats.sum()
//        text = sum.toString()
//    }

    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = text,
                size = 64.sp
            )
        }


        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "C")
            CalculatorButton(modifier = Modifier.weight(1f), text = "MC")
            CalculatorButton(modifier = Modifier.weight(1f), text = "MR")
            CalculatorButton(modifier = Modifier.weight(1f), text = "M+")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "⌫")
            CalculatorButton(modifier = Modifier.weight(1f), text = "±")
            CalculatorButton(modifier = Modifier.weight(1f), text = "%")
            CalculatorButton(modifier = Modifier.weight(1f), text = "÷")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "7")
            CalculatorButton(modifier = Modifier.weight(1f), text = "8")
            CalculatorButton(modifier = Modifier.weight(1f), text = "9")
            CalculatorButton(modifier = Modifier.weight(1f), text = "×")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "4")
            CalculatorButton(modifier = Modifier.weight(1f), text = "5")
            CalculatorButton(modifier = Modifier.weight(1f), text = "6")
            CalculatorButton(modifier = Modifier.weight(1f), text = "-")
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(1f), text = "1")
            CalculatorButton(modifier = Modifier.weight(1f), text = "2")
            CalculatorButton(modifier = Modifier.weight(1f), text = "3")
            CalculatorButton(modifier = Modifier.weight(1f), text = "+")
        }

        // TODO see if this is fixable, if not, make my own layout again i guess 🤷
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            CalculatorButton(modifier = Modifier.weight(2.1f), text = "0")
            CalculatorButton(modifier = Modifier.weight(1f), text = ".")
            CalculatorButton(
                modifier = Modifier.weight(1f),
                backgroundColor = LocalAccentColor.current,
                textColor = LocalTextOnAccentColor.current,
                text = "=",
            )
        }
    }
}


@Composable
internal fun CalculatorButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = LocalButtonColor.current,
    textColor: Color = LocalTextOnButtonColor.current,
    text: String,
) {
    Box(
        modifier = modifier
            .heightIn(min = 64.dp)
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            size = 24.sp,
            color = textColor,
        )
    }
}

//sealed interface Option {
//    sealed interface Number : Option {
//        data class Integer(val int: Int) : Number
//        data class Floating(val float: Float) : Number
//    }
//
//    data class Operation(val symbol: Symbol) : Option
//}
//
//enum class Symbol {
//    Plus,
//    Minus,
//    Multiply,
//    Divide
//}

internal interface Calculator {
    val display: Flow<String>

    fun digit(i: Int)

    fun decimal()

    fun operation(op: Operation)

    enum class Operation {
        Plus, Minus, Multiplier, Divide, Equal
    }
}

internal class CalculatorImpl : Calculator {
    // TODO it needs to have something observable as a state of the display
    private val _display = MutableStateFlow("0")
    override val display: Flow<String> = _display

    override fun digit(i: Int) {
        _display.value = if (_display.value != "0") {
            "${_display.value}$i"
        } else {
            i.toString()
        }
    }

    override fun decimal() {
        TODO("Not yet implemented")
    }

    override fun operation(op: Calculator.Operation) {
        TODO("Not yet implemented")
    }
}