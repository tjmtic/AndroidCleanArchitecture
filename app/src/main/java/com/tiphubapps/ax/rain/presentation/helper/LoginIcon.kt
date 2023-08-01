package com.tiphubapps.ax.rain.presentation.helper

import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role.Companion.Image
import com.tiphubapps.ax.rain.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LoginIcon(shape: Shape = RoundedCornerShape(24.dp)) {
    Box(
        modifier = Modifier
            .size(120.dp)
            .clip(shape)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.logo1024),
            contentDescription = "Login Icon",
            modifier = Modifier.fillMaxSize()
        )
    }
}
