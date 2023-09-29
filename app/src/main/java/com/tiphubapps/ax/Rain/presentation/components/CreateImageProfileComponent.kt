package com.tiphubapps.ax.Rain.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.tiphubapps.ax.Rain.R


@Composable
fun CreateImageProfile(modifier: Modifier = Modifier, url: String) {
    Surface(
        modifier = Modifier
            .size(154.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.LightGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Image(
            /*painter = if (url != null) {
                rememberImagePainter(
                    data = url, builder = {
                        crossfade(true)
                        scale(Scale.FIT)
                    })
            } else {
                painterResource(id = R.drawable.blank_profile_picture)
            },*/
            painter = rememberImagePainter(
                data = url, builder = {
                    crossfade(true)
                    scale(Scale.FIT)
                    placeholder(R.drawable.line_rain2a)
                }),
            //painter = painterResource(id = R.drawable.line_rain2a),
            contentDescription = "profile image",
            modifier = modifier.size(135.dp),
            contentScale = ContentScale.Crop
        )

    }

}