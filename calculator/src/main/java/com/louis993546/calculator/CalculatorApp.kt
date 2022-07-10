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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalButtonColor
import com.louis993546.metro.LocalTextOnAccentColor
import com.louis993546.metro.LocalTextOnButtonColor
import com.louis993546.metro.Text

@Composable
fun CalculatorApp(
    modifier: Modifier = Modifier,
) {
    val calculator: Calculator = rememberCalculator()

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
                // TODO adjust text size accordingly
                //  https://stackoverflow.com/questions/63971569/androidautosizetexttype-in-jetpack-compose
                calculator.smallDisplay?.run {
                    Text(text = this, size = 16.sp)
                }
                Text(text = calculator.bigDisplay, size = 64.sp)
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
                calculator.operation(Calculator.Operation.Backspace)
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
            ) { calculator.operation(Calculator.Operation.Equal) }
        }
    }
}

// TODO on press, change the color to theme color or fallback color

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
            .clickable(
                onClick = onClick,
                role = Role.Button,
                onClickLabel = text,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            size = 24.sp,
            color = textColor,
        )
    }
}
