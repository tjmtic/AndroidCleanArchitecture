package com.tiphubapps.ax.rain.presentation.screen.home

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tiphubapps.ax.rain.presentation.components.ButtonComponent
import com.tiphubapps.ax.rain.presentation.components.GPTProfileScreen3
import com.tiphubapps.ax.domain.model.User
import com.google.gson.JsonObject


@Composable
fun ReceiveItem(user: User?,
                qrImage: Bitmap?,
                users: JsonObject?,
                navController: NavHostController) {

    val showUserList = remember { mutableStateOf(false) }

    val coverImage = user?.coverImage /*images?.asJsonArray.let {
        it?.let {
            var url = "none"
            for (image in it) {
                if (image.asJsonObject.get("type").asString.equals("cover")) {
                    url = image.asJsonObject.get("url").asString
                }
            }

            url
        }
    }*/

    val profileImage = user?.profileImage /*get("images")?.asJsonArray.let {
        it?.let {
            var url = "none"
            for (image in it) {
                if (image.asJsonObject.get("type").asString.equals("profile")) {
                    url = image.asJsonObject.get("url").asString
                }
            }

            url
        }
    }*/

    val currentUserInfo = User(
        0,
        "0",
        null,
        null,
        user?.payerBalance,//.get("payerBalance")?.asInt,
        user?.receiverBalance,//get("receiverBalance")?.asInt,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        //user?.email,//get("email")?.asString,
        user?.name,//get("name")?.asString,
        null,
        null,
        null,
        null,
        null,
        coverImage,
        profileImage,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
    null)

    fun toggleUserList(){
        showUserList.value = !showUserList.value
    }


    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.Black
    ) {

            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(
                        end = 2.dp,
                    )
                    .verticalScroll(rememberScrollState())
            ) {

                if(!showUserList.value) {
                   // ButtonComponent(text = "Contributors", onClick = { toggleUserList() }, enabled = true)

                    //FullReceiveItemComponent(user = user, qrImage)
                    if (qrImage != null) {
                        GPTProfileScreen3(user = currentUserInfo, qrImage = qrImage, { toggleUserList() })


                    }

                   /* Text(text = "Test Text", style = MaterialTheme.typography.body1)
                    user?.get("name")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    user?.get("token")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body2,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    user?.get("id")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("name")?.asString?.let { RatingComponent(rating = it) }

                    Image(
                        painter = rememberImagePainter(
                            data = qrImage, builder = {
                                crossfade(true)
                                scale(Scale.FIT)
                            }),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(125.dp),
                        contentScale = ContentScale.FillWidth
                    )

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
                                            .fillMaxWidth()
                                            .height(125.dp),
                                        contentScale = ContentScale.FillWidth
                                    )
                                } else {
                                    Text(
                                        text = "ELSE NO IMAGES???? ${image.asJsonObject.get("type")}",
                                        style = MaterialTheme.typography.body1
                                    )

                                }
                            }
                        }
                    } ?: run {
                        Text(text = "NO IMAGES????", style = MaterialTheme.typography.body1)
                    }

                    // user?.get("images")?.asString?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                    user?.get("payerBalance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("receiverBalance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("available")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }
                    user?.get("balance")?.asString?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.body1
                        )
                    }*/
                }

                else {

                    ButtonComponent(text = "Show View", onClick = { toggleUserList() }, enabled = true)



                    users?.let {
                        it.get("senders")?.let {
                        for (userData in it.asJsonArray) {
                            /*Text(
                                    text = userData.asJsonObject.get("name").asString,
                                    style = MaterialTheme.typography.body1
                                )*/
                            UserListItem(userData.asJsonObject)
                        }
                    }
                        Spacer(modifier = Modifier.height(8.dp))

                     /*   it.get("receivers")?.let {
                            for (userData in it.asJsonArray) {
                                /*Text(
                                    text = userData.asJsonObject.get("name").asString,
                                    style = MaterialTheme.typography.body1
                                )*/
                                UserListItem(userData.asJsonObject)
                            }
                        }*/

                    }

                }


            }

    }
}