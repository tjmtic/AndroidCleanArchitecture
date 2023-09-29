package com.tiphubapps.ax.Rain.presentation.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.tiphubapps.ax.Rain.presentation.components.RatingComponent
import com.tiphubapps.ax.Rain.presentation.navigation.Screen
import com.tiphubapps.ax.Rain.ui.theme.ItemBackgroundColor
import com.tiphubapps.ax.domain.model.User
import com.google.gson.JsonObject

@Composable
fun UserListContent(allUsers: LazyPagingItems<User>, navController: NavHostController, currentUser: JsonObject) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
    ) {
        items(
            items = allUsers,
            key = { user ->
                user.pk
            }
        ) { user ->
            if (user != null) {
                UserListItem(user = user, navController = navController)
            }
        }
    }
}

@Composable
fun UserListItem(user: User, navController: NavHostController) {
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
                    navController.navigate(route = Screen.UserDetails.passUserId(user.userId.toString()))
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            user.images?.let {
                Image(
                    modifier = Modifier
                        .padding(
                            end = 4.dp,
                        )
                        .width(120.dp),
                    painter = rememberImagePainter(
                        data = it, builder = {
                            crossfade(true)
                            scale(Scale.FILL)
                        }),
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
            }

            Column(Modifier
                .height(IntrinsicSize.Max)
                .padding(
                    end = 2.dp,
                )) {
                user.name?.let { Text(text = it, style = MaterialTheme.typography.body1) }
                Spacer(modifier = Modifier.height(4.dp))
                user.payerEmail?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.body2,
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                user.name?.let { RatingComponent(rating = it) }
            }
        }
    }
}






