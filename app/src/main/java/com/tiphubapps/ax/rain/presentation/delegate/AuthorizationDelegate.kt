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

