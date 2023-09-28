package com.tiphubapps.ax.rain.presentation.screen.splash

//ViewModel State-Events (i.e. Event Interface) (Events that affect State)
sealed class SplashStateEvent {
    object LoadingStarted: SplashStateEvent()
    object LoadingFinished: SplashStateEvent()
    data class LoadingError(val message: String): SplashStateEvent()
    object TokenLoaded: SplashStateEvent()
    object LoginFinished : SplashStateEvent()
    object LoginFailed : SplashStateEvent()
}