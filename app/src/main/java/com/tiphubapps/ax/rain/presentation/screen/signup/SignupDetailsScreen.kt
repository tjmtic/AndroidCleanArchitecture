package com.tiphubapps.ax.rain.presentation.screen.details

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tiphubapps.ax.rain.presentation.screen.login.ForgotItem
import com.tiphubapps.ax.rain.presentation.screen.login.LoginDetailsContent
import com.tiphubapps.ax.rain.presentation.screen.login.SignupItem
import com.tiphubapps.ax.rain.ui.theme.AppContentColor


@Composable
fun SignupDetailsScreen(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    val uiState = viewModel.state.collectAsState()

    //var username by remember { mutableStateOf("") }
    //var password by rememberSaveable { mutableStateOf("") }

    fun navigateHome(){
        onNavigateToHome()
    }


    fun onLoginClick(){
       // viewModel.postLogin()//username, password)
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
            println("${it}")

            when(uiState.value.viewState){
                //logindetailscontent
                is LoginViewModel.LoginUiState.Login -> LoginDetailsContent(/*navController = navController,*/
                                                                            {onLoginClick()},
                    {onDisplaySignup()},
                    {onDisplayForgot()},
                                                                            true)
                //signupitem
                is LoginViewModel.LoginUiState.Signup -> SignupItem({username, password -> onSignupClick(username, password)},
                    {onDisplayLogin()},
                    {onDisplayForgot()},
                    true)
                //forgotitem
                is LoginViewModel.LoginUiState.Forgot -> ForgotItem({ username -> onForgotClick(username) },
                    {onDisplayLogin()},
                    {onDisplaySignup()},
                    true)
                //After Login Success
                is LoginViewModel.LoginUiState.Home -> {
                    LaunchedEffect(uiState){
                        navigateHome()
                    }
                }
                //error
                else -> LoginDetailsContent(/*navController = navController, */{ onLoginClick() }, {onDisplaySignup()},
                    {onDisplayForgot()}, true)
            }

            //oAuth Login

        })
}

