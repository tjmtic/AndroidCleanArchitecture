package com.tiphubapps.ax.rain.presentation.screen.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.useCase.AuthUseCases
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

open class AuthedViewModel @Inject constructor(private val useCases: AuthUseCases, coroutineContextProvider: CoroutineContextProvider): ViewModel() {


    //TODO: AuthState
    // combine(isLoggedIn, currentToken, isTokenValid) = Authed, NotAuthed


    //TODO: Organization of flows for producers and consumers
    // Hot / Cold best practices and implementations
    //PRODUCER: isLoggedIn, currentToken
    //CONSUMER: useCaseAuthGetToken, Flow

    //Hard Default to Log Out
    private val _isLoggedIn = MutableStateFlow<Boolean>(false)

    private val _currentToken = MutableStateFlow<String>("")
    val currentToken: StateFlow<String> = _currentToken.asStateFlow()

    //TODO: var/val Best Practice, private set / public get /////
    var curTok by mutableStateOf("")
        private set

    /////////////////////////////////////////////////////////////

    /*private val tokenRefreshed: Flow<Boolean> = flow<Boolean> {
        println("REFREHSING FLOW EMIT TOKEN.1..")
        while (true) {
            //TODO: Remove this delay, Automatic Propagation with Hot Flow?
            delay(5000)
            println("REFREHSING FLOW EMIT TOKEN.2..${viewModelScope}")
            val isTokenValid = handleResponse(useCases.useCaseAuthGetToken())

            //////////////////////////////////////
            //TODO: Are these the same thing then?
            // Research hot/cold flow implementation
            _isLoggedIn.value = isTokenValid
            emit(isTokenValid)
            //////////////////////////////////////
        }
    }//.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)*/


    //TODO: Change this to isTokenValid
    lateinit var isLoggedIn: StateFlow<Boolean?>

    //TODO: AuthState = combine(isLoggedIn, isTokenValid){isLoggedIn, isTokenValid -> isLoggedIn && isTokenValid}
    //authState = AuthState()

    init {
        println("auth view model init")
        viewModelScope.launch {
            //Begin Scoped Collection
            isLoggedIn = getFlow(useCases.useCaseAuthGetTokenFlow()).stateIn(viewModelScope,
                                                                            SharingStarted.WhileSubscribed(5000),
                                                                            null)
        }
    }

    private fun handleResponse(response: UseCaseResult<String>): Boolean {
        when(response) {
            is UseCaseResult.UseCaseSuccess -> {
                response.data.let {
                    _currentToken.value = it
                     return@handleResponse isTokenValid(it)
                }
            }
            else -> { return false }
        }
    }


    //TODO: Move String Validation down to AuthRepo??
    private suspend fun getFlow(response: UseCaseResult<Flow<String>>): Flow<Boolean> {
        return when(response) {
            is UseCaseResult.UseCaseSuccess -> {
                response.data.let {
                    return@getFlow it.map{ it1 -> isTokenValid(it1)}
                }
            }
            //Re-try on failure
            else -> {   delay(1000)
                        getFlow(useCases.useCaseAuthGetTokenFlow())
            }
        }
    }

    private fun isTokenValid(token: String): Boolean {
        // Check if a valid token exists in storage, and if it's not expired
        // You can implement your token validation logic here
        // Return true if the token is valid, false otherwise
        if(token.isNotEmpty() && token.isNotBlank()) {
            println("----Valid token: ${isLoggedIn.value} $token")
            return true
        }
        return false
    }

    fun logout() {
        // Clear the token from storage
        // Set the authentication state to "logged out"
        // Navigate to the login screen
        // Clear clearables
        _isLoggedIn.value = false

        //TODO:
        //useCases.logout()
    }
}
