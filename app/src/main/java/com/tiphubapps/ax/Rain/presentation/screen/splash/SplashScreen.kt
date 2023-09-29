package com.tiphubapps.ax.Rain.presentation.screen.splash

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.tiphubapps.ax.Rain.presentation.screen.details.SplashViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tiphubapps.ax.Rain.presentation.helper.LoginIcon
import com.tiphubapps.ax.Rain.ui.theme.AppContentColor

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    val events = viewModel.eventBus.collectAsStateWithLifecycle(initialValue = SplashUiEvent.SPLASHING)

    Scaffold(
        contentColor = MaterialTheme.colors.AppContentColor,
        content = {
            println("$it")
            LoginIcon()
        }
    )

        // Check authentication status and navigate accordingly
    LaunchedEffect(events) {
        println("State Update According to SplashViewModel")

        if(events.value == SplashUiEvent.SPLASHED && state.value.isLoggedIn){
            println("Logged in According to SplashViewModel, going to HOME")
            onNavigateToHome()
        }
        else{
            onNavigateToLogin()
        }
    }


    // Display a loading indicator or app logo
    // Customize the UI and user experience here
}