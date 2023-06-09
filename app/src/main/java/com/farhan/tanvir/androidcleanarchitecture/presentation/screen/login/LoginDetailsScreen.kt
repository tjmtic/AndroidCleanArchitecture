package com.farhan.tanvir.androidcleanarchitecture.presentation.screen.login

import GPTForgot
import GPTLogin
import GPTSignUp
import android.app.Activity
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.farhan.tanvir.androidcleanarchitecture.MainActivity
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginDetailsContent
import com.farhan.tanvir.androidcleanarchitecture.presentation.screen.details.LoginViewModel
import com.farhan.tanvir.androidcleanarchitecture.ui.theme.AppContentColor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.*


@Composable
fun LoginDetailsScreen(
    navController: NavHostController,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {




    val uiState = viewModel.uiState.collectAsState()//WithLifecycle()
    val currentToken = viewModel.currentToken.collectAsState()

    //var username by remember { mutableStateOf("") }
    //var password by rememberSaveable { mutableStateOf("") }

    //val activity = LocalContext.current as MainActivity

    val backgroundColor by animateColorAsState(when(uiState.value){ is LoginViewModel.LoginUiState.Login -> {Color.Blue}
                                                                    else -> Color.Magenta})
    val backgroundColor2 by animateColorAsState(when(uiState.value){ else -> Color.Magenta})

    fun navigateHome(){
        onNavigateToHome()
    }


    fun onLoginClick(username: String, password: String){
       // activity.timer();
        //viewModel.start();
        viewModel.postLogin(username, password)
    }

    fun onDisplayLogin(){
        viewModel.showLogin();
    }

    fun onSignupClick(username: String, password: String){
       // viewModel.timer();
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

    fun onEvent(event: LoginViewModel.LoginViewEvent){
        viewModel.onEvent(event)
    }




    Scaffold(
       /* topBar={
            LoginDetailsTopBar(navController)
        },*/
        contentColor = MaterialTheme.colors.AppContentColor,
        content = {
            //icon and title

            when(currentToken.value){
                "" -> Log.d("TIME123", "Empty TOKEN VALUE in LOGIN VIEWMODEL")
                "0x0" -> Log.d("TIME123", "No TOKEN VALUE in LOGIN VIEWMODEL")
                else -> LaunchedEffect(uiState){
                    navigateHome()
                }
            }

            when(uiState.value){
                //logindetailscontent
                is LoginViewModel.LoginUiState.Login -> GPTLogin({username, password -> onLoginClick(username, password)},
                    {onDisplaySignup()},
                    {onDisplayForgot()}, { event ->
                        onEvent(event)
                    }
                );/*LoginDetailsContent(navController = navController,
                                                                            {onLoginClick()},
                    {onDisplaySignup()},
                    {onDisplayForgot()},
                                                                            true)*/
                //signupitem
                is LoginViewModel.LoginUiState.Signup -> GPTSignUp({ username, password -> onSignupClick(username, password)},
                                                                    {onDisplayLogin()})
                /*SignupItem({ username, password -> onSignupClick(username, password)},
                    {onDisplayLogin()},
                    {onDisplayForgot()},
                    true)*/
                //forgotitem
                is LoginViewModel.LoginUiState.Forgot -> GPTForgot({ username -> onForgotClick(username) },
                                                                    {onDisplayLogin()})
                /*ForgotItem({ username -> onForgotClick(username) },
                    {onDisplayLogin()},
                    {onDisplaySignup()},
                    true)*/
                //After Login Success
                is LoginViewModel.LoginUiState.Home -> {
                    LaunchedEffect(uiState){
                        navigateHome()
                    }
                }
                //error
                else -> GPTLogin({username, password -> onLoginClick(username, password)},
                    {onDisplaySignup()},
                    {onDisplayForgot()},
                    { event ->
                        onEvent(event)
                    })/*LoginDetailsContent(navController = navController, { onLoginClick() }, {onDisplaySignup()},
                    {onDisplayForgot()}, true)*/
            }

            //oAuth Login

        })
}

