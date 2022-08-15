package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.RatingComponent
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.ItemBackgroundColor
import com.google.gson.JsonObject


@Composable
fun UserItem(user: JsonObject?, navController: NavHostController) {
    Card(
        modifier = Modifier
            .padding(top = 8.dp)
            .height(180.dp)
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
                    navController.navigate(route = Screen.UserDetails.passUserId(user?.get("userId").toString()))
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier
                .height(IntrinsicSize.Max)
                .padding(
                    end = 2.dp,
                )) {
                Text(text = "Test Text", style = MaterialTheme.typography.body1)
                user?.get("name")?.asString?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                Spacer(modifier = Modifier.height(4.dp))
                user?.get("payerEmail")?.asString?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body2,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                user?.get("name")?.asString?.let { RatingComponent(rating = it) }
            }
        }
    }
}