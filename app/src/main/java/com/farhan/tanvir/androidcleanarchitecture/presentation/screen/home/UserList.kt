package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*

import androidx.compose.runtime.Composable

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.model.UserList

@Composable
fun UserListContent(allUsers: LazyPagingItems<User>,
                    usersWithReservations: LazyPagingItems<User>,
                    usersWithoutReservations: LazyPagingItems<User>,
                    navController: NavHostController,
                    selectedUsers: List<User>,
                    getUserValue: (Boolean, User) -> Unit
) {

    fun getCheckboxValue(selected: Boolean, user: User){
        getUserValue(selected, user)
    }

    Row(
        modifier = Modifier
            //.height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "With", style = MaterialTheme.typography.body1)
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            items(
                items = usersWithReservations,
                key = { user ->
                    user.pk
                }
            ) { user ->
                if (user != null) {
                    UserListItem(user = user,
                                checked = selectedUsers.contains(user),
                                onCheckboxSelected = { getCheckboxValue(it, user)})
                }
            }
        }
    }
    Row(
        modifier = Modifier
            //.height(IntrinsicSize.Max)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Without", style = MaterialTheme.typography.body1)
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            items(
                items = usersWithoutReservations,
                key = { user ->
                    user.pk
                }
            ) { user ->
                if (user != null) {
                    UserListItem(user = user,
                                checked = selectedUsers.contains(user),
                                onCheckboxSelected = { getCheckboxValue(it, user)})
                }
            }
        }
    }
}






