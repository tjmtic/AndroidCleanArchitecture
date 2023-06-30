package com.tiphubapps.ax.rain.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextButtonComponent(text: String, onClick: () -> Unit, enabled: Boolean) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        contentPadding = PaddingValues(
            start = 0.dp,
            top = 0.dp,
            end = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Text(text)
    }
}