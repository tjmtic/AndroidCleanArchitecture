package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.graphics.Bitmap
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.*
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonObject


@Composable
fun SendItem(user: JsonObject?,
             users: JsonObject?,
             currentUser: JsonObject?,
             onSetSelectedUser: (String) -> Unit,
                navController: NavHostController,
                ) {

    val showUserList = remember { mutableStateOf(false) }

    fun toggleUserList(){
        showUserList.value = !showUserList.value
    }
    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        elevation = 4.dp,
        backgroundColor = Color.Blue
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                    Log.d("TIME123", "Console Log Clicked on USER CARD!" + user);
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                    .height(IntrinsicSize.Max)
                    .padding(
                        end = 2.dp,
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                if(!showUserList.value) {
                    ButtonComponent(text = "Following", onClick = { toggleUserList() }, enabled = true)
                    //ButtonComponent(text = "Show View", onClick = { toggleUserList() }, enabled = false)
                    var isFull = false;
                    user?.get("name")?.isJsonNull?.let {
                        println("full")
                        FullSendItemComponent(user = user)
                        isFull = true;
                    } ?: run {
                        println("e,pty")

                        EmptySendItemComponent(user = currentUser)
                    }

                    if(!isFull) {
                        EmptySendItemComponent(user = currentUser)
                    }

                    println("user")
                    println(user)
                    println("currentuser")
                    println(currentUser)
                    /*Text(text = "Test Text", style = MaterialTheme.typography.body1)
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
                   // ButtonComponent(text = "Receivers", onClick = { toggleUserList() }, enabled = false)
                    ButtonComponent(text = "Show View", onClick = { toggleUserList() }, enabled = true)

                    UserListComponent(users = users, onClick = {id -> onSetSelectedUser(id) ; toggleUserList()})
                    /*users?.let {
                        /*it.get("senders")?.let {
                            for (userData in it.asJsonArray) {
                                /*Text(
                                    text = userData.asJsonObject.get("name").asString,
                                    style = MaterialTheme.typography.body1
                                )*/
                                UserListItem(userData.asJsonObject)
                            }
                        }*/
                        Spacer(modifier = Modifier.height(8.dp))

                        it.get("receivers")?.let {
                        for (userData in it.asJsonArray) {
                            /*Text(
                                    text = userData.asJsonObject.get("name").asString,
                                    style = MaterialTheme.typography.body1
                                )*/
                            UserListItem(userData.asJsonObject, {id -> onSetSelectedUser(id)})
                        }
                    }*/

                   // }
                }


            }
        }
    }
}