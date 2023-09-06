package com.tiphubapps.ax.rain.presentation.screen.home


import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tiphubapps.ax.rain.presentation.components.CameraComponent
import com.tiphubapps.ax.rain.ui.theme.AppContentColor
import com.tiphubapps.ax.rain.ui.theme.AppThemeColor
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tiphubapps.ax.rain.presentation.components.WebViewComponent
import com.tiphubapps.ax.rain.presentation.screen.details.UserDetailsViewModel

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavHostController,
               onNavigateToLogin: () -> Unit,
               onNavigateToProfile: () -> Unit,
               viewModel: HomeViewModel = hiltViewModel()) {

    val systemUiController = rememberSystemUiController()
    val systemBarColor = MaterialTheme.colors.AppThemeColor
    val scaffoldState = rememberScaffoldState()

    //val allUsers = viewModel.getAllUsers.collectAsLazyPagingItems()
    //viewModel.getCurrentUser()
    val currentUser = viewModel.currentUser.collectAsState()
    val selectedUser = viewModel.selectedUser.collectAsState()

    val currentUsers = viewModel.allUsers.collectAsState()

    val uiState = viewModel.uiState.collectAsState()
    val uiStateLogin = viewModel.uiStateLogin.collectAsState()
    val uiStateCamera = viewModel.uiStateCamera.collectAsState()
    val uiStateEvent = viewModel.uiStateEvent.collectAsState()

    val token = viewModel.token.collectAsState()


    val backgroundColor by animateColorAsState(
        when(uiStateEvent.value){
            is HomeViewModel.EventUiState.TIP -> Color.Green
            else -> Color.Magenta
        }
    )

    var showWebView1 = remember { mutableStateOf(false) }
    var extra = token
    fun toggleWebview1(){
        showWebView1.value = !showWebView1.value
    }


    fun showSend(){
        viewModel.showSend()
    }

    fun showReceive(){
        viewModel.showReceive()
    }

    fun showCamera(){
        viewModel.showCamera()
    }



    fun hideCamera(id: String){
        viewModel.hideCamera()
        println("set selected user with userid..." + id)
        val fields = id.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val lastField = fields[fields.size - 1]
        println("set selected user with userid.22.." + id)

        viewModel.setSelectedById(lastField)
    }

    fun toggleCamera(){
        viewModel.toggleCamera()
    }

    fun setSelectedUserById(id: String){
        println("set selected user...")
        viewModel.setSelectedById(id)
    }

    fun setUnselectedUser(){
        viewModel.setUnselectedUser();
    }

    fun sendTip(){
        viewModel.sendTip();
    }


    println("THIS HOME VIEW TOKEN=="+token.value)

    SideEffect {
        systemUiController.setStatusBarColor(
            color = systemBarColor
        )
    }

    Scaffold(
        backgroundColor = backgroundColor,
        contentColor = MaterialTheme.colors.AppContentColor,
        topBar = {
            HomeTopBar({onNavigateToProfile()}, {showSend()}, {showReceive()}, {toggleCamera()})
        },
        content = {

            when(uiStateLogin.value) {
                is HomeViewModel.LoginUiState.Invalid -> {
                    LaunchedEffect(uiState) {
                        println("invalid Login State, loggin out $it")
                        //viewModel.performLogout()
                        //onNavigateToLogin()
                    }
                }

                else -> {}
            }
            if(showWebView1.value){
                Log.d("TIME123", "Showing webview1:"+extra)
                WebViewComponent(url = "https://tiphubapps.com/webview/checkout/?token=" + extra)
            }
            when(uiStateCamera.value) {
                is HomeViewModel.CameraUiState.Enabled -> CameraComponent(Modifier.fillMaxSize(), { id -> hideCamera(id)})
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
                            onUnsetSelectedUser = {setUnselectedUser()},
                            navController = navController,
                            onTip = {sendTip()},
                        )
                        is HomeViewModel.HomeUiState.Default -> SendItem(
                            user = selectedUser.value,
                            users = currentUsers.value,
                            currentUser = currentUser.value,
                            onSetSelectedUser = {id -> setSelectedUserById(id)},
                            onUnsetSelectedUser = {setUnselectedUser()},
                            navController = navController,
                            onTip = {sendTip()},
                        )
                        is HomeViewModel.HomeUiState.Error -> SendItem(
                            user = selectedUser.value,
                            users = currentUsers.value,
                            currentUser = currentUser.value,
                            onSetSelectedUser = {id -> setSelectedUserById(id)},
                            onUnsetSelectedUser = {setUnselectedUser()},
                            navController = navController,
                            onTip = {sendTip()},
                        )
                    }

                }
            }
        },
        bottomBar = {
            HomeBottomBar({showSend()}, {showReceive()}, {toggleWebview1()}, {onNavigateToProfile()}, {toggleCamera()})
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                // FAB onClick
                toggleCamera()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        },
        scaffoldState = scaffoldState,
        isFloatingActionButtonDocked = true,
        floatingActionButtonPosition = FabPosition.Center
    )
}

