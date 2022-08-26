package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun CheckboxComponent(text: String, value: Boolean, onValueChanged: (Boolean) -> Unit) {
    Row {
        Checkbox(
            checked = value,
            onCheckedChange = onValueChanged,
            //enabled = true,
            colors = CheckboxDefaults.colors(Color.Green)
        )
        Text(text = text, fontSize = 16.sp, modifier = Modifier.align(Alignment.CenterVertically))
    }
}