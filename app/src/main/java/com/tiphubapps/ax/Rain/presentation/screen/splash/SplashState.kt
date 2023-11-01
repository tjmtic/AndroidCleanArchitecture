package com.tiphubapps.ax.Rain.presentation.screen.splash

//ViewModel State (i.e. Status Implementation)
data class SplashState(
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)