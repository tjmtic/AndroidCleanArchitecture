package com.tiphubapps.ax.rain.presentation.screen.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.useCase.AuthUseCases
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

open class AuthedViewModel @Inject constructor(useCases: AuthUseCases, coroutineContextProvider: CoroutineContextProvider): ViewModel() {

    //Hard Default to Log Out
    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    private val _currentToken = MutableStateFlow<String>("")
    val currentToken: StateFlow<String> = _currentToken

    init {
        viewModelScope.launch (
            coroutineContextProvider.io, CoroutineStart.DEFAULT
        ){

            when(val response = useCases.useCaseAuthGetToken()) {

                is UseCaseResult.UseCaseSuccess -> {
                    response.data.let {
                        _currentToken.value = it
                        // Check if a valid token exists during initialization
                        if (isTokenValid(it)) {
                            _isLoggedIn.value = true
                        } else {
                            _isLoggedIn.value = false
                        }
                    }
                }
                else -> { _isLoggedIn.value = false }

            }

        }
    }

    private fun isTokenValid(token: String): Boolean {
        // Check if a valid token exists in storage, and if it's not expired
        // You can implement your token validation logic here
        // Return true if the token is valid, false otherwise
        if(token.isNotEmpty() && token.isNotBlank()) {
            return true
        }

        return false
    }

    fun logout() {
        // Clear the token from storage
        // Set the authentication state to "logged out"
        // Navigate to the login screen

        _isLoggedIn.value = false
    }
}
