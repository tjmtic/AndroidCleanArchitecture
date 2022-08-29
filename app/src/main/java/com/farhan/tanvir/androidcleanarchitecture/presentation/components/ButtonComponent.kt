package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun ButtonComponent(text: String, onClick: () -> Unit, enabled: Boolean) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
        contentPadding = PaddingValues(
            start = 30.dp,
            top = 12.dp,
            end = 30.dp,
            bottom = 12.dp
        ),
        modifier = Modifier
                    .fillMaxWidth()
            .padding(16.dp, 0.dp, 16.dp, 34.dp),
        shape = RoundedCornerShape(25.dp)
    ) {
        Text(text, style = TextStyle(color = Color.White))
    }
}