package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonArray
import com.google.gson.JsonObject


@Composable
fun UserListItem(user: JsonObject?) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(50.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.ItemBackgroundColor
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                    Log.d("TIME123", "Console Log Clicked on USER CARD!" + user);
                    user?.get("socketId")?.asString?.let {
                        println("Clicked user list item with socket id: ${it}")
                }},
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                        style = MaterialTheme.typography.body1
                    )
                }


                user?.get("images")?.asJsonArray.let {
                    it?.let {
                        for (image in it) {
                            if(image.asJsonObject.get("type").asString.equals("cover")) {
                                Image(
                                    painter = rememberImagePainter(
                                        data = image.asJsonObject.get("url").asString, builder = {
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

                            else if(image.asJsonObject.get("type").asString.equals("profile")) {
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
                            }

                            else {
                                Text(text = "ELSE NO IMAGES???? ${image.asJsonObject.get("type")}", style = MaterialTheme.typography.body1)

                            }
                        }
                    }
                } ?: run {
                    Text(text = "NO IMAGES????", style = MaterialTheme.typography.body1)
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
}