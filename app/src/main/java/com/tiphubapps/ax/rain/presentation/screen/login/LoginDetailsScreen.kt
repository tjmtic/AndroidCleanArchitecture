package com.tiphubapps.ax.rain.presentation.screen.login

import GPTForgot
import GPTSignUp
import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tiphubapps.ax.rain.presentation.screen.details.LoginViewModel
import com.tiphubapps.ax.rain.ui.theme.AppContentColor


@Composable
fun LoginDetailsScreen(
    //navController: NavHostController,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {



    val state = viewModel.state.collectAsStateWithLifecycle()
    //val uiState = viewModel.uiState.collectAsState()//WithLifecycle()
    //val networkUiState = viewModel.networkUiState.collectAsState()
    val currentToken = viewModel.localValueFlow.collectAsStateWithLifecycle()//viewModel.currentToken.collectAsState()

    val isLoggedIn = viewModel.isLoggedIn.collectAsStateWithLifecycle()

    var hasError by remember { mutableStateOf(false) }
    val context = LocalContext.current

    //var username by remember { mutableStateOf("") }
    //var password by rememberSaveable { mutableStateOf("") }

    //val activity = LocalContext.current as MainActivity

    //val backgroundColor by animateColorAsState(when(state.value.viewState){ is LoginViewModel.LoginUiState.Login -> {Color.Blue}
    //                                                                else -> Color.Magenta})
    //val backgroundColor2 by animateColorAsState(when(state.value.viewState){ else -> Color.Magenta})

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


    /*LaunchedEffect(hasError) {
        if (hasError) {
            // Perform the one-off event, in this case, vibrate the device
            PerformVibration(context)

            // Optional: Reset the error state after a delay (e.g., 2 seconds)
            delay(2000)
            hasError = false
        }
    }*/

    Scaffold(
       /* topBar={
            LoginDetailsTopBar(navController)
        },*/
        contentColor = MaterialTheme.colors.AppContentColor,
        content = {
            //icon and title

            //////WHat is this for???
            //Fixed later on? Other Effects?

            when(isLoggedIn.value) {
                true -> {
                    LaunchedEffect(isLoggedIn) {
                    println("Logged in According to authViewModel, going to HOME")
                    //viewModel.performLogout()
                    onNavigateToHome()
                } }

                else -> {

                }
            }

            when(currentToken.value){
                "" -> Log.d("TIME123", "${it}Empty TOKEN VALUE in LOGIN VIEWMODEL")
                "0x0" -> Log.d("TIME123", "No TOKEN VALUE in LOGIN VIEWMODEL")
                else -> LaunchedEffect(currentToken.value){
                    println("TIME123 TOKEN VALUE IN LOGIN SCREEN: ${currentToken.value}")
                    /*if (state.value.viewState is LoginViewModel.LoginUiState.Home)*/ navigateHome()
                }
            }

            when(state.value.viewState){
                //logindetailscontent
                is LoginViewModel.LoginUiState.Login -> GPTLogin(state.value, {username, password -> onLoginClick(username, password)},
                    {onDisplaySignup()},
                    {onDisplayForgot()},
                    { event ->
                        onEvent(event)
                    },
                    { email ->
                        onEvent(LoginViewModel.LoginViewEvent.EmailChanged(email))
                    },
                    { password ->
                        onEvent(LoginViewModel.LoginViewEvent.PasswordChanged(password))
                    },
                    {
                        onEvent(LoginViewModel.LoginViewEvent.LoginClicked)
                    })

                //signupitem
                is LoginViewModel.LoginUiState.Signup -> GPTSignUp({ username, password -> onSignupClick(username, password)},
                                                                    {onDisplayLogin()})

                //forgotitem
                is LoginViewModel.LoginUiState.Forgot -> GPTForgot({ username -> onForgotClick(username) },
                                                                    {onDisplayLogin()})

                //After Login Success
                //Sure this works?
                is LoginViewModel.LoginUiState.Home -> {
                    LaunchedEffect(state.value.viewState){

                        /*if (state.value.viewState is LoginViewModel.LoginUiState.Home)*/ navigateHome()
                    }
                }
                //error
                else -> GPTLogin(state.value, {username, password -> onLoginClick(username, password)},
                    {onDisplaySignup()},
                    {onDisplayForgot()},
                    { event ->
                        onEvent(event)
                    },
                    { email ->
                        onEvent(LoginViewModel.LoginViewEvent.EmailChanged(email))
                    },
                    { password ->
                        onEvent(LoginViewModel.LoginViewEvent.PasswordChanged(password))
                    },
                    {
                        onEvent(LoginViewModel.LoginViewEvent.LoginClicked)
                    }
                )
            }

            //oAuth Login

        })
}

@Composable
@Preview
fun previewLoginScreen(){
    LoginDetailsScreen({})
}

