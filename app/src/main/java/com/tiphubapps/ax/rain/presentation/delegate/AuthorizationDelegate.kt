package com.tiphubapps.ax.rain.presentation.delegate

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.useCase.AuthUseCases
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine

sealed class AuthState {
    object AUTHED: AuthState()
    object NOTAUTHED: AuthState()
    object REFRESH: AuthState()
}
interface AuthorizationDelegate {
    //val isTokenValid : Flow<Boolean?>
    var authState : StateFlow<AuthState>
    //fun cancelScope()
}
class AuthorizationDelegateImpl @Inject constructor(useCases: AuthUseCases, coroutineContextProvider: CoroutineContextProvider):
    AuthorizationDelegate {
    //TODO: Organization of flows for producers and consumers
    // Hot / Cold best practices and implementations
    //PRODUCER: isRefreshing, isTokenValid -->authState<--
    //CONSUMER: useCaseAuthGetTokenFlow -->authEvent??<--

    private val scope = CoroutineScope(coroutineContextProvider.io)
    private val authJob: Job

    private val _isRefreshing = MutableStateFlow<Boolean>(false)
    private val _isTokenValid: StateFlow<Boolean> = handleResponse(useCases.useCaseAuthGetTokenFlow())

    override var authState : StateFlow<AuthState> = combine(_isRefreshing, _isTokenValid){ ref: Boolean, tok:Boolean ->
        println("Auth State Updating... $ref $tok")
            if (ref) {
                AuthState.REFRESH
            } else if (tok) {
                AuthState.AUTHED
            } else {
                AuthState.NOTAUTHED
            }
    }.stateIn(scope, SharingStarted.WhileSubscribed(5000), AuthState.REFRESH)

    ////////////////////////////////////////////////////
    //TODO: var/val Best Practice, private set / public get /////
    private var curTok by mutableStateOf("")
        private set

    /////////////////////////////////////////////////////////////

    init {
        println("auth view model DELEGATE init")
        //TODO: This job is completed once the initial flow is returned
        // cancelling it will do nothing after it has returned

        //TODO: Should this collect be started by the UI?
        authJob = scope.launch (
         //   coroutineContextProvider.io + coroutineExceptionHandler, CoroutineStart.DEFAULT
        ) {
            //Begin Scoped Collection
            _isTokenValid.collect{
                println("COLLECTED TOKENVALIDITY!!!! $it $scope")
            }
            println("auth view model DELEGATE complete")
        }
        println("auth view model DELEGATE end")
    }

    fun cancelScope(){
        scope.cancel("")
        authJob.cancel()
    }

    private fun handleResponse(response: UseCaseResult<StateFlow<Boolean>>): StateFlow<Boolean> {
        return when(response) {
            is UseCaseResult.UseCaseSuccess -> {
                response.data.let {
                    return@handleResponse it
                }
            }
            else -> {
                MutableStateFlow(false).asStateFlow()
                //throw error?
            }
        }
    }
}
