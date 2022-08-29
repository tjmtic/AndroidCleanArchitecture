package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.home


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.farhan.tanvir.androidcleanarchitecture.presentation.components.CameraComponent
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavHostController,
               onNavigateToProfile: () -> Unit,
               viewModel: HomeViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor
    //val allUsers = viewModel.getAllUsers.collectAsLazyPagingItems()
    //viewModel.getCurrentUser()
    val currentUser = viewModel.currentUser.collectAsState()
    val selectedUser = viewModel.selectedUser.collectAsState()

    val currentUsers = viewModel.allUsers.collectAsState()

    val uiState = viewModel.uiState.collectAsState()
    val uiStateCamera = viewModel.uiStateCamera.collectAsState()

    fun showSend(){
        viewModel.showSend()
    }

    fun showReceive(){
        viewModel.showReceive()
    }

    fun showCamera(){
        viewModel.showCamera()
    }

    fun hideCamera(){
        viewModel.hideCamera()
    }

    fun toggleCamera(){
        viewModel.toggleCamera()
    }

    fun setSelectedUserById(id: String){
        println("set selected user...")
        viewModel.setSelectedById(id)
    }


    println("THIS HOME VIEW TOKEN=="+viewModel.token)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        backgroundColor = Color.Blue,
        contentColor = MaterialTheme.colors.AppContentColor,
        topBar = {
            HomeTopBar({onNavigateToProfile()}, {showSend()}, {showReceive()}, {toggleCamera()})
        },
        content = {
            when(uiStateCamera.value) {
                is HomeViewModel.CameraUiState.Enabled -> CameraComponent(Modifier.fillMaxSize(), {hideCamera()})
                else -> {
                    when (uiState.value) {
                        is HomeViewModel.HomeUiState.Receive -> ReceiveItem(
                            user = currentUser.value,
                            viewModel.qrImage,
                            users = currentUsers.value,
                            navController = navController
                        )
                        //is HomeViewModel.HomeUiState.Send -> CameraComponent(Modifier.fillMaxSize())
                        // is HomeViewModel.HomeUiState.Send -> UserItem(user = currentUser.value, users = currentUsers.value, navController = navController)
                        is HomeViewModel.HomeUiState.Send -> SendItem(
                            user = selectedUser.value,
                            users = currentUsers.value,
                            currentUser = currentUser.value,
                            onSetSelectedUser = {id -> setSelectedUserById(id)},
                            navController = navController
                        )
                        is HomeViewModel.HomeUiState.Default -> SendItem(
                            user = selectedUser.value,
                            users = currentUsers.value,
                            currentUser = currentUser.value,
                            onSetSelectedUser = {id -> setSelectedUserById(id)},
                            navController = navController
                        )
                        is HomeViewModel.HomeUiState.Error -> SendItem(
                            user = selectedUser.value,
                            users = currentUsers.value,
                            currentUser = currentUser.value,
                            onSetSelectedUser = {id -> setSelectedUserById(id)},
                            navController = navController
                        )
                    }
                }
            }
        },
        bottomBar = {
            HomeBottomBar({showSend()}, {showReceive()}, {toggleCamera()})
        }
    )
}

