package com.tiphubapps.ax.Rain.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tiphubapps.ax.Rain.presentation.screen.home.UserListItem
import com.google.gson.JsonObject

@Composable
fun UserListComponent(users: JsonObject?, onClick: (String) -> Unit) {
    users?.let {
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

        it.get("contributors")?.let {
            for (userData in it.asJsonArray) {
                /*Text(
                        text = userData.asJsonObject.get("name").asString,
                        style = MaterialTheme.typography.body1
                    )*/

                UserListItem(userData.asJsonObject, {id -> onClick(id)})
                //UserListItem(userData.asJsonObject, {id -> onClick(id)})
                //UserListItem(userData.asJsonObject, {id -> onSetSelectedUser(id)})
            }
        }

    }
}