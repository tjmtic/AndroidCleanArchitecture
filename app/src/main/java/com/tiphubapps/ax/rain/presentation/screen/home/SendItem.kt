package com.tiphubapps.ax.rain.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tiphubapps.ax.domain.model.User
import com.google.gson.JsonObject
import com.tiphubapps.ax.rain.presentation.components.GPTProfileScreen
import com.tiphubapps.ax.rain.presentation.components.GPTProfileScreen2
import com.tiphubapps.ax.rain.presentation.components.UserListComponent


@Composable
fun SendItem(user: User?,
             users: JsonObject?,
             currentUser: User?,
             onSetSelectedUser: (String) -> Unit,
             onUnsetSelectedUser: () -> Unit,
             onTip: () -> Unit,
                navController: NavHostController,
                ) {

    val interactionSource = remember { MutableInteractionSource() }
    var buttonScale by remember { mutableStateOf(1f) }

    val showUserList = remember { mutableStateOf(false) }

    val coverImage = currentUser?.coverImage/*.get("images")?.asJsonArray.let {
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

    val profileImage = currentUser?.profileImage /*.get("images")?.asJsonArray.let {
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

    val userImage = user?.profileImage

    val currentUserInfo = User(
        0,
        "0",
        null,
        null,
        currentUser?.payerBalance,//.get("payerBalance")?.asInt,
        currentUser?.receiverBalance,//.get("receiverBalance")?.asInt,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        //currentUser?.get("email")?.asString,
        currentUser?.name,//.get("name")?.asString,
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
        null
        )

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
        /*Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
                .clickable {
                    Log.d("TIME123", "Console Log Clicked on USER CARD!" + user);
                },
            verticalAlignment = Alignment.CenterVertically
        ) {*/
            Column(
                Modifier
                    .fillMaxHeight()
                    .padding(
                        bottom = 2.dp,
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                if(!showUserList.value) {
                    //ButtonComponent(text = "Following", onClick = { toggleUserList() }, enabled = true)
                    //ButtonComponent(text = "Show View", onClick = { toggleUserList() }, enabled = false)
                    var isFull = false;
                    user?.let {
                        println("full")
                        //FullSendItemComponent(user = user)
                        GPTProfileScreen2 (user, userImage!!, {onUnsetSelectedUser()}, {onTip()})
                       // ButtonComponent(text = "Following", onClick = { toggleUserList() }, enabled = true)
                        isFull = true;
                    } ?: run {
                        println("e,pty")
                        GPTProfileScreen (currentUserInfo) { toggleUserList() }
                        // EmptySendItemComponent(user = currentUser)
                    }

                    if(!isFull) {
                        GPTProfileScreen ( currentUserInfo, {toggleUserList()} )

                       // EmptySendItemComponent(user = currentUser)
                    }

                    println("user")
                    println(user)
                    println("userInfo")
                    println(currentUserInfo)
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
                   // ButtonComponent(text = "Show View", onClick = { toggleUserList() }, enabled = true)
                    Button(
                        onClick = { toggleUserList() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Magenta),
                        modifier = Modifier
                            .fillMaxWidth()
                            .scale(buttonScale)
                            .pointerInput(interactionSource) {
                                detectTapGestures(
                                    onPress = { buttonScale = 0.95f },
                                    //l = { buttonScale = 1f }
                                )
                                //onPointerUp { buttonScale = 1f }
                                //onPointerCancel { buttonScale = 1f }
                            }
                            .padding(vertical = 16.dp)
                            .background(color = Color.Magenta, shape = RoundedCornerShape(25.dp))
                    ) {
                        Text(
                            text = "Close",
                            style = MaterialTheme.typography.button,
                            modifier = Modifier.padding(horizontal = 8.dp),
                            color = Color.White
                        )
                    }

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


        //    }
        }
    }
}