package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text

import androidx.compose.runtime.Composable

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.NonDisposableHandle.parent

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UserListContent(title: String,
                    users: LazyPagingItems<User>,
                    getUserValue: (Boolean, User) -> Unit
) {

    //Function Hoisted in UserListItem
    fun getCheckboxValue(selected: Boolean, user: User){
        getUserValue(selected, user)
    }
            val groupedItems = users.itemSnapshotList.items.groupBy { it.reserved }
    LazyColumn{
                groupedItems.forEach { (reserved, userGroup) ->

                    stickyHeader {
                        Text(
                            text = if (reserved) "Have Reservations" else "Need Reservations" ,
                            color = Color.White,
                            modifier = Modifier
                                .background(Color.Gray)
                                .padding(5.dp)
                                .fillMaxWidth()
                        )
                    }

                    items(items = userGroup,
                        key = { user ->
                            user.pk
                        }) { user ->
                        user?.let {
                            UserListItem(
                                user = user,
                                onCheckboxSelected = { getCheckboxValue(it, user) })
                        }
                    }
                }
            }

            /*Text(text = title, style = MaterialTheme.typography.body1)
            LazyColumn(
               // modifier = Modifier.verticalScroll(scrollState),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                items(
                    items = users,
                    key = { user ->
                        user.pk
                    }
                ) { user ->
                    if (user != null) {
                        UserListItem(
                            user = user,
                            onCheckboxSelected = { getCheckboxValue(it, user) })
                    }
                }
            }*/



}






