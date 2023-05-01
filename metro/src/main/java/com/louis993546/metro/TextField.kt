package com.louis993546.metro

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = modifier
            .heightIn(min = 48.dp)
            .background(color = Color.White)
            .border(3.dp, color = LocalAccentColor.current) // FIXME: should only have accent during focus
            .padding(horizontal = 12.dp)
            .padding(vertical = 6.dp),
        contentAlignment = Alignment.CenterStart,
    ) {
        // FIXME: Make all of this configurable
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = TextStyle(
                fontFamily = fontFamily,
                fontSize = 22.sp,
                fontWeight = FontWeight.Medium
            ),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None
            )
        )
    }
}