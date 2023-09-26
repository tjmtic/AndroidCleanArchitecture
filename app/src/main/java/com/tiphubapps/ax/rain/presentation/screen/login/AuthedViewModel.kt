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
import androidx.lifecycle.asFlow
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

open class AuthedViewModel @Inject constructor(private val useCases: AuthUseCases, private val coroutineContextProvider: CoroutineContextProvider): ViewModel() {

    //TODO: Organization of flows for producers and consumers
    // Hot / Cold best practices and implementations
    //PRODUCER: isRefreshing, isTokenValid -->authState<--
    //CONSUMER: useCaseAuthGetTokenFlow -->authEvent??<--

    //Hard Default to Logged Out with Refresh
    //private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    /*private val _isTokenValid: Flow<Boolean?> = getFlow(useCases.useCaseAuthGetTokenFlow()).stateIn(viewModelScope,
                                                                                                            SharingStarted.WhileSubscribed(5000),
                                                                                                            null)*/
    val isTokenValid : StateFlow<Boolean> = getFlow(useCases.useCaseAuthGetTokenFlow())
    private val _isTokenValid : StateFlow<Boolean> = getFlow(useCases.useCaseAuthGetTokenFlow())
    //val authState : LiveData<Boolean> = _isTokenValid
    var authState : StateFlow<AuthState> = combine(_isRefreshing, _isTokenValid){ ref: Boolean, tok:Boolean ->

        println("Auth State Updating... $ref $tok")
        //tok?.let {
            if (ref) {
                AuthState.REFRESH
            } else if (tok) {
                AuthState.AUTHED
            } else {
                AuthState.NOTAUTHED
            }
        //} ?: AuthState.REFRESH
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),AuthState.REFRESH)

    val authJob: Job


    ////////////////////////////////////////////////////
    //TODO: var/val Best Practice, private set / public get /////
    var curTok by mutableStateOf("")
        private set

    /////////////////////////////////////////////////////////////

    init {
        println("auth view model init")
        //TODO: This job is completed once the initial flow is returned
        // cancelling it will do nothing after it has returned
        authJob = viewModelScope.launch (
         //   coroutineContextProvider.io + coroutineExceptionHandler, CoroutineStart.DEFAULT
        ) {
            //Begin Scoped Collection
            _isTokenValid.collect(){
                println("COLLECTED TOKENVALIDITY!!!! $it")
            }

            //TODO: Implement token refreshing?
            /*_isTokenValid = getFlow(useCases.useCaseAuthGetTokenFlow()).stateIn(viewModelScope,
                                                                            SharingStarted.WhileSubscribed(5000),
                                                                            null)*/


            /*authState = combine(_isRefreshing, _isTokenValid){ ref: Boolean, tok:Boolean? ->

                println("Auth State Updating... $ref $tok")
                tok?.let {
                    if (ref) {
                        AuthState.REFRESH
                    } else if (tok) {
                        AuthState.AUTHED
                    } else {
                        AuthState.NOTAUTHED
                    }
                } ?: AuthState.REFRESH
            }.stateIn(viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                null)*/

            println("auth view model complete")
        }

        println("auth view model end")
    }

    private fun getFlow(response: UseCaseResult<StateFlow<Boolean>>): StateFlow<Boolean> {
        return when(response) {
            is UseCaseResult.UseCaseSuccess -> {
                response.data.let {
                    return@getFlow it
                }
            }
            else -> {
                MutableStateFlow(false).asStateFlow()
                //throw error?
            }
        }
    }

    private fun getLiveData(response: UseCaseResult<LiveData<Boolean>>): LiveData<Boolean> {
        return when(response) {
            is UseCaseResult.UseCaseSuccess -> {
                response.data.let {
                    println("getting live data for token: ${it.value}")
                    return@getLiveData it
                }
            }
            else -> {
                //flowOf(false)
                MutableLiveData(false)
                //throw error?
            }
        }
    }

    fun login() {
       // _isLoggedIn.value = true
    }

    fun logout() {
        // Clear the token from storage
        // Set the authentication state to "logged out"
        // Navigate to the login screen
        // Clear clearables
        //_isLoggedIn.value = false

        //TODO:
        //useCases.logout()
    }

    override fun onCleared() {
        super.onCleared()
        authJob.cancel()
        println("CLEARED AUTH JOBS")
        //_isTokenValid.col
    }


    sealed class AuthState {
        object AUTHED: AuthState()
        object NOTAUTHED: AuthState()
        object REFRESH: AuthState()
    }
}
