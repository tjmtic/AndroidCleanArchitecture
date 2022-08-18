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
            start = 10.dp,
            top = 10.dp,
            end = 10.dp,
            bottom = 10.dp
        ),
        modifier = Modifier
                    .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 20.dp),
        shape = RoundedCornerShape(22.dp)
    ) {
        Text(text)
    }
}