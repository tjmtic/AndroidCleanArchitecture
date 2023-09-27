package com.tiphubapps.ax.rain.presentation.screen.splash

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.tiphubapps.ax.rain.presentation.screen.details.SplashViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tiphubapps.ax.rain.presentation.helper.LoginIcon
import com.tiphubapps.ax.rain.presentation.screen.login.AuthedViewModel
import com.tiphubapps.ax.rain.ui.theme.AppContentColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        /* topBar={
             LoginDetailsTopBar(navController)
         },*/
        contentColor = MaterialTheme.colors.AppContentColor,
        content = {
            println("$it")
            LoginIcon()
        }
    )

        // Check authentication status and navigate accordingly
    LaunchedEffect(state.value) {
        println("State Update According to SplashViewModel")

            //delay(3000)
        if(state.value.isLoggedIn && state.value.viewState == SplashViewModel.SplashUiState.Ready){
            println("Logged in According to SplashViewModel, going to HOME")
            onNavigateToHome()
        }
        else{
            onNavigateToLogin()
        }
            /*when (isLoggedIn.value.isLoggedIn) {
                AuthedViewModel.AuthState.AUTHED -> {
                    println("Logged in According to authViewModel, going to HOME")
                    navigateHome()
                }

                AuthedViewModel.AuthState.NOTAUTHED -> {
                    //Do Nothing
                    navigateLogin()
                }

                AuthedViewModel.AuthState.REFRESH -> {
                    //Show Loading?
                }

                else -> {
                    //Show Error?
                }
            }*/
    }


    // Display a loading indicator or app logo
    // Customize the UI and user experience here
}