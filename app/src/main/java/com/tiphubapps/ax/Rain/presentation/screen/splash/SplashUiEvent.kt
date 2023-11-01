package com.tiphubapps.ax.Rain.presentation.screen.splash

//UI State-Events (i.e. Status Interface) (Events that affect UI)
sealed class SplashUiEvent {
    object SPLASHING: SplashUiEvent()
    object SPLASHED: SplashUiEvent()
    data class Error(val msg: String = "Error"): SplashUiEvent()
}