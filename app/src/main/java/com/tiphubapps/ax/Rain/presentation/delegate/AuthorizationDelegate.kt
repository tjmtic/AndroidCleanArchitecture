package com.tiphubapps.ax.Rain.presentation.delegate

import kotlinx.coroutines.flow.StateFlow

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

