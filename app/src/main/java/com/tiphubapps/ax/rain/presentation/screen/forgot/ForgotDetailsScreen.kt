package com.tiphubapps.ax.rain.presentation.screen.forgot

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tiphubapps.ax.rain.presentation.screen.details.ForgotViewModel
import com.tiphubapps.ax.rain.presentation.screen.login.ForgotItem
import com.tiphubapps.ax.rain.ui.theme.AppContentColor


@Composable
fun ForgotDetailsScreen(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
    viewModel: ForgotViewModel = hiltViewModel(),
) {

    val uiState = viewModel.uiState.collectAsState()

    //var username by remember { mutableStateOf("") }
    //var password by rememberSaveable { mutableStateOf("") }

    fun navigateHome(){
        onNavigateToHome()
    }


    fun onLoginClick(){
        viewModel.postLogin()//username, password)
    }

    fun onDisplayLogin(){
        viewModel.showLogin();
    }

    fun onSignupClick(username: String, password: String){
        viewModel.postSignup(username, password)
    }

    fun onDisplaySignup(){
        viewModel.showSignup();
    }

    fun onForgotClick(username: String){
        viewModel.postForgot(username)
    }

    fun onDisplayForgot(){
        viewModel.showForgot();
    }




    Scaffold(
       /* topBar={
            LoginDetailsTopBar(navController)
        },*/
        contentColor = MaterialTheme.colors.AppContentColor,
        backgroundColor = Color.Blue,
        content = {
            //icon and title

            when(uiState.value){

                //forgotitem
                is ForgotViewModel.LoginUiState.Forgot -> ForgotItem({ username -> onForgotClick(username) },
                    {onDisplayLogin()},
                    {onDisplaySignup()},
                    true)
                //After Login Success
                /*is LoginViewModel.LoginUiState.Home -> {
                    LaunchedEffect(uiState){
                        navigateHome()
                    }
                }*/
                //error
                //else -> ForgotDetailsContent(navController = navController, { onLoginClick() }, {onDisplaySignup()},
                 //   {onDisplayForgot()}, true)
                else -> {}
            }

            //oAuth Login

        })
}

