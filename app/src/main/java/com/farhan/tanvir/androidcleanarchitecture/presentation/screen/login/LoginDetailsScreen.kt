package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details

import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.presentation.navigation.Screen
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login.ForgotItem
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login.LoginItem
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login.SignupItem
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppThemeColor
import com.farhan.tanvir.domain.model.User


@Composable
fun LoginDetailsScreen(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
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
        backgroundColor = MaterialTheme.colors.AppThemeColor,
        content = {
            //icon and title

            when(uiState.value){
                //logindetailscontent
                is LoginViewModel.LoginUiState.Login -> LoginDetailsContent(navController = navController,
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
                else -> LoginDetailsContent(navController = navController, { onLoginClick() }, {onDisplaySignup()},
                    {onDisplayForgot()}, true)
            }

            //oAuth Login

        })
}

