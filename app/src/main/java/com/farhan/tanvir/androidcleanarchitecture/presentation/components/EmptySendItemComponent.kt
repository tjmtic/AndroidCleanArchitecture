package com.farhan.tanvir.androidcleanarchitecture.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.gson.JsonObject

@Composable
fun EmptySendItemComponent(user: JsonObject?) {


    Spacer(modifier = Modifier.height(4.dp))

    Row(
        modifier = Modifier
            .height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        user?.get("images")?.asJsonArray.let {
            it?.let {
                for (image in it) {
                    if (image.asJsonObject.get("type").asString.equals("profile")) {
                        Image(
                            painter = rememberImagePainter(
                                data = image.asJsonObject.get("url").asString,
                                builder = {
                                    crossfade(true)
                                    scale(Scale.FIT)
                                }),
                            contentDescription = null,
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            Modifier
                .height(IntrinsicSize.Max)
                .padding(
                    end = 2.dp,
                )
        ) {
            user?.get("name")?.asString?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.White)
                )
            }
            //Text(text = "it", style = MaterialTheme.typography.body1)

            user?.get("payerBalance")?.asString?.let {
                Text(
                    text = "Remaining Tokens: ${it}",
                    style = TextStyle(color = Color.White)
                )
            }
        }
    }
    Text(text = "Select a user to tip by scanning a barcode or picking from your recent list", style = TextStyle(color = Color.White))


    Text(text = "The image below is your cover image. Contributors will see this photo while tipping you. Tap the profile button to change this photo in the profile menu.", style = TextStyle(color = Color.White))

    user?.get("images")?.asJsonArray.let {
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
                        modifier = Modifier
                            .width(375.dp)
                            .height(375.dp),
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
        }
    }
}