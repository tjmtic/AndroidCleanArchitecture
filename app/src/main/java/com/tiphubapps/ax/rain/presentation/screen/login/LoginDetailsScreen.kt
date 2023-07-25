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
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tiphubapps.ax.rain.presentation.screen.details.LoginViewModel
import com.tiphubapps.ax.rain.ui.theme.AppContentColor


@Composable
fun LoginDetailsScreen(
    //navController: NavHostController,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {



    val state = viewModel.state.collectAsState()//WithLifecycle()
    val uiState = viewModel.uiState.collectAsState()//WithLifecycle()
    val networkUiState = viewModel.networkUiState.collectAsState()
    val currentToken = viewModel.currentToken.collectAsState()

    var hasError by remember { mutableStateOf(false) }
    val context = LocalContext.current

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

            when(currentToken.value){
                "" -> Log.d("TIME123", "Empty TOKEN VALUE in LOGIN VIEWMODEL")
                "0x0" -> Log.d("TIME123", "No TOKEN VALUE in LOGIN VIEWMODEL")
                else -> LaunchedEffect(uiState){
                    navigateHome()
                }
            }

            when(uiState.value){
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
                is LoginViewModel.LoginUiState.Home -> {
                    LaunchedEffect(uiState){
                        navigateHome()
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

