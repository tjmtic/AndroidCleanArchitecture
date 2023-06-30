package com.tiphubapps.ax.rain.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.tiphubapps.ax.rain.R
import com.google.gson.JsonObject


@Composable
fun GPTProfileScreen2(user: JsonObject?, url: String, onClick: () -> Unit, onTip: () -> Unit) {
    Log.d("TIME123", "PRIFLE SCREEN CHECK: " + url)
    Box(
        modifier = Modifier.fillMaxSize().defaultMinSize(400.dp)
    ) {
        // Background image
        /*user?.get("images")?.asJsonArray.let {
            it?.let {
                for (image in it) {
                    if (image.asJsonObject.get("type").asString.equals("cover")) {
                        Image(
                            painter = rememberImagePainter(
                                data = image.asJsonObject.get("url").asString,
                                builder = {
                                    crossfade(true)
                                    scale(Scale.FIT)
                                }),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                        )
                    }
                }
            }
        }*/



        /*Image(
            painter = rememberImagePainter(
                data = url,
                builder = {
                    crossfade(true)
                    scale(Scale.FIT)
                }
            ),
            contentDescription = "title default",
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )*/



        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
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
                    painter = rememberImagePainter(
                        data = url,
                        builder = {
                            crossfade(true)
                            scale(Scale.FIT)
                            placeholder(R.drawable.line_rain2a)
                        }
                    ),
                    contentDescription = "some",
                    modifier = Modifier.size(400.dp),
                    contentScale = ContentScale.FillBounds,
                )
            }

            // Profile icon with two lines of text
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    user?.get("name")?.let {
                        Text(
                            text = it.asString,
                            style = MaterialTheme.typography.h5.merge(
                                TextStyle(color = Color.White)
                            ),
                            textAlign = TextAlign.Start
                        )
                    }

                }
            }

            // Paragraph of text
            Text(
                text = "Tip Total:",
                style = MaterialTheme.typography.body1.merge(
                    TextStyle(color = Color.White)
                ),
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
//            style = TextStyle(color = Color.White)
            )
            user?.get("payerBalance")?.asString?.let {
                Text(
                    text = " ${it}",
                    style = MaterialTheme.typography.body1.merge(
                        TextStyle(color = Color.White)
                    ),
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
//            style = TextStyle(color = Color.White)
                )
            }

            Button(
                onClick = { onTip() },
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(.95f)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "SEND TIP",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )
            }



            Button(
                onClick = { onClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(1f)
                    .padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Disconnect",
                    style = MaterialTheme.typography.button,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileScreen2() {
    GPTProfileScreen2(JsonObject(), "", {}, {})
}