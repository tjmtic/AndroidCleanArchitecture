package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home


import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(navController: NavHostController,
               onNavigateToProfile: () -> Unit,
               viewModel: HomeViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor
    //val allUsers = viewModel.getAllUsers.collectAsLazyPagingItems()
    //viewModel.getCurrentUser()
    val currentUser = viewModel.currentUser.collectAsState()

    val uiState = viewModel.uiState.collectAsState()

    fun showSend(){
        viewModel.showSend()
    }

    fun showReceive(){
        viewModel.showReceive()
    }


    println("THIS HOME VIEW TOKEN=="+viewModel.token)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        contentColor = MaterialTheme.colors.AppContentColor,
        topBar = {
            HomeTopBar({onNavigateToProfile()}, {showSend()}, {showReceive()})
        },
        content = {
            when(uiState.value) {
                is HomeViewModel.HomeUiState.Receive -> ReceiveItem(user = currentUser.value, navController = navController)
                is HomeViewModel.HomeUiState.Send -> UserItem(user = currentUser.value, navController = navController)
                is HomeViewModel.HomeUiState.Default -> UserItem(user = currentUser.value, navController = navController)
                is HomeViewModel.HomeUiState.Error -> UserItem(user = currentUser.value, navController = navController)
            }
        }
    )
}

