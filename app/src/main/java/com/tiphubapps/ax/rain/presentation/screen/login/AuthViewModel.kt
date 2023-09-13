package com.tiphubapps.ax.rain.presentation.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.useCase.AuthUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch

class AuthViewModel (useCases: AuthUseCases, coroutineContextProvider: CoroutineContextProvider): ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    private val _currentToken = MutableLiveData<String>()
    val currentToken = _currentToken

    init {
        viewModelScope.launch (
            coroutineContextProvider.io, CoroutineStart.DEFAULT
        ){

            when(val response = useCases.useCaseAuthGetToken()) {

                is UseCaseResult.UseCaseSuccess -> {
                    _currentToken.value = response.data
                    // Check if a valid token exists during initialization
                    if (isTokenValid()) {
                        _isLoggedIn.value = true
                    } else {
                        _isLoggedIn.value = false
                    }
                }
                else -> {}

            }

        }
    }

    private fun isTokenValid(): Boolean {
        // Check if a valid token exists in storage, and if it's not expired
        // You can implement your token validation logic here
        // Return true if the token is valid, false otherwise
        return true
    }

    fun logout() {
        // Clear the token from storage
        // Set the authentication state to "logged out"
        // Navigate to the login screen
    }
}
