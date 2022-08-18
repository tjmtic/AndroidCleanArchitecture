package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.farhan.tanvir.androidcleanarchitecture.R

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