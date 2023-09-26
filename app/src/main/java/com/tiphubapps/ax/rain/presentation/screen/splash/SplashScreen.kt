package com.tiphubapps.ax.rain.presentation.screen.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.tiphubapps.ax.rain.presentation.screen.details.SplashViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tiphubapps.ax.rain.presentation.screen.login.AuthedViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel,
    navigateHome: () -> Unit,
    navigateLogin: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    //SplashState -- isLoggedIn && isFinishedLoading

    // Check authentication status and navigate accordingly
    LaunchedEffect(state.value) {
        if(state.value.isLoggedIn && state.value.viewState == SplashViewModel.SplashUiState.Ready){
            println("Logged in According to SplashViewModel, going to HOME")
            navigateHome()
        }
        else{
            navigateLogin()
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