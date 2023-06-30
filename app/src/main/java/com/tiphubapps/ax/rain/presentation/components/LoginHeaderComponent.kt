package com.tiphubapps.ax.rain.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale

@Composable
fun LoginHeaderComponent(title: String, url: String) {
    Spacer(modifier = Modifier.height(50.dp))

    Image(
            painter = rememberImagePainter(
                data = url, builder = {
                    crossfade(true)
                    scale(Scale.FIT)
                }),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = title,
            style = TextStyle(color = Color.White, fontSize = 40.sp)//MaterialTheme.typography.h3
        )

}