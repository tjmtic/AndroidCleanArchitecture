package com.abyxcz.disneycodechallenge.androidcodechallenge.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ButtonComponent(text: String, onClick: () -> Unit, enabled: Boolean) {
    Button(
        enabled = enabled,
        onClick = { onClick() },
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
        Text(text)
    }
}