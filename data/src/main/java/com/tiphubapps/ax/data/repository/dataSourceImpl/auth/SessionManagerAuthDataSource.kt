package com.tiphubapps.ax.data.repository.dataSourceImpl.auth

import com.tiphubapps.ax.data.repository.dataSource.auth.AuthDataSource
import com.tiphubapps.ax.data.util.SessionManager
import javax.inject.Inject

class SessionManagerAuthDataSource @Inject constructor(private val sessionManager: SessionManager) :
    AuthDataSource {
    override fun saveToken(token: String) {
        sessionManager.setUserToken(token)
    }

    override fun getToken(): String? {
        return sessionManager.getUserToken()
    }
}