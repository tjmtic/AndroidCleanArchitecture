package com.tiphubapps.ax.rain.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.gson.JsonObject
import androidx.compose.ui.draw.clip


@Composable
fun UserListItem(user: JsonObject?, onClickUser: (String) -> Unit = {}) {

    fun getUserId(id: String){
        onClickUser(id)
    }
    Card(
        modifier = Modifier
            .padding(8.dp)
            //.height(50.dp)
            .fillMaxWidth(),
        elevation = 12.dp,
        border = BorderStroke(1.5.dp, Color.LightGray),
        backgroundColor = Color.Magenta,

    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                    Log.d("TIME123", "Console Log Clicked on USER CARD!" + user);
                    user?.get("socketId")?.asString?.let {
                        println("Clicked user list item with socket id: ${it}")
                    }
                    user?.get("_id")?.asString?.let {
                        println("Clicked user list item with id: ${it}")
                        getUserId(it)
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {

                var selected = false;
                user?.get("images")?.asJsonArray.let {
                    it?.let {
                        for (image in it) {
                            if (image.asJsonObject.get("type").asString.equals("cover")) {
                                selected = true
                                Surface(
                                    modifier = Modifier
                                        .size(77.dp)
                                        .padding(5.dp),
                                    shape = CircleShape,
                                    border = BorderStroke(0.5.dp, Color.LightGray),
                                    elevation = 4.dp,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                                ) {
                                    Image(
                                        painter = rememberImagePainter(
                                            data = image.asJsonObject.get("url").asString,
                                            builder = {
                                                crossfade(true)
                                                scale(Scale.FIT)
                                            }),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(48.dp)
                                            .clip(MaterialTheme.shapes.medium)
                                            .background(MaterialTheme.colors.primary),
                                        contentScale = ContentScale.FillWidth
                                    )
                                }

                            }


                            /* else if(image.asJsonObject.get("type").asString.equals("profile")) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = image.asJsonObject.get("url").asString, builder = {
                                            crossfade(true)
                                            scale(Scale.FIT)
                                        }),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(125.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                           }*/
                        }



                        if(!selected){
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
                                        data = "https://tip-hub.s3.amazonaws.com/users/img/5e22b8a4bf397f08932de490-profile.png",
                                        builder = {
                                            crossfade(true)
                                            scale(Scale.FIT)
                                        }),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(50.dp)
                                        .height(50.dp)
                                        .padding(end = 8.dp),
                                    contentScale = ContentScale.FillWidth
                                )
                            }

                        }
                    }
                }

            user?.get("name")?.asString?.let {
                Text(
                    text = it,
                    style = TextStyle(color = Color.White)
                )
            }



               /* user?.get("socketId")?.asString?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body1
                    )
                }*/


        }
    }
}