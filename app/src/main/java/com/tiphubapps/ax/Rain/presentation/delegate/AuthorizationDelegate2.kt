package com.tiphubapps.ax.Rain.presentation.delegate

import kotlinx.coroutines.flow.StateFlow

sealed class AuthState2 {
    object AUTHED: AuthState()
    object NOTAUTHED: AuthState()
    object REFRESH: AuthState()
}
interface AuthorizationDelegate2 {
    //val isTokenValid : Flow<Boolean?>
    var authState : StateFlow<AuthState>
    //fun cancelScope()
}

