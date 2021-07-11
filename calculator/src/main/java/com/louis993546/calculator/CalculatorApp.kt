package com.louis993546.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louis993546.metro.*

@Composable
fun CalculatorApp(
    modifier: Modifier = Modifier,
) {
//    val items = remember { mutableStateListOf<Option>(Option.Number.Integer(0)) }
//    var text by remember { mutableStateOf("0") }

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
            modifier = Modifier.weight(1f).fillMaxWidth(),
        ) {
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                text = "0",
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
fun CalculatorButton(
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