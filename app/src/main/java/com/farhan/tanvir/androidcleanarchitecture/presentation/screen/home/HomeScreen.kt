package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home


import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Path.Companion.combine
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.model.UserList
import com.google.accompanist.systemuicontroller.rememberSystemUiController

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun HomeScreen(navController: NavHostController, viewModel: HomeViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor

    val allUsers = viewModel.getAllUsers.collectAsLazyPagingItems()
    val usersWithReservations = viewModel.usersWithReservations.collectAsLazyPagingItems()
    val usersWithoutReservations = viewModel.usersWithoutReservations.collectAsLazyPagingItems()

    val getSelectedUsersFlow = viewModel.getSelectedUsersFlow.collectAsState(emptyList())

    val uiState = viewModel.uiState.collectAsState()

    fun navigate(){
       println("Navigate!")
    }

    fun getUserValue(selected: Boolean, user : User){
        viewModel.updateSelected(selected, user)
    }


    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        contentColor = MaterialTheme.colors.AppContentColor,
        topBar = {
            HomeTopBar()
        },
        content = {
            UserListContent(allUsers = allUsers,
                            usersWithReservations = usersWithReservations,
                            usersWithoutReservations = usersWithoutReservations,
                            navController = navController,
                            selectedUsers = getSelectedUsersFlow.value,
                            getUserValue = {selected, user -> getUserValue(selected, user)},)
        },
        bottomBar = {
            HomeBottomBar("Continue", {navigate()}, (uiState.value is UserListUiState.Success
                                                            && (uiState.value as UserListUiState.Success).enabled) )
        }
    )
}

