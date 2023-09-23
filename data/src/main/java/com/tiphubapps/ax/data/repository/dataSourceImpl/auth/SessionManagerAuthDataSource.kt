package com.tiphubapps.ax.data.repository.dataSourceImpl.auth

import com.tiphubapps.ax.data.repository.dataSource.auth.AuthDataSource
import com.tiphubapps.ax.data.util.SessionManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SessionManagerAuthDataSource @Inject constructor(private val sessionManager: SessionManager) :
    AuthDataSource {
    override fun saveToken(token: String) {
        sessionManager.setUserToken(token)
    }

    override fun getToken(): String? {
        return sessionManager.getUserToken()
    }

    override fun getTokenFlow(): Flow<String>? {
        return _tokenFlow
    }

    private val _tokenFlow = flow<String> {
        while(true){
            getToken()?.let { emit(it) }
            delay(5000)
        }
    }
}