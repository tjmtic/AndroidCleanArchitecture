package com.tiphubapps.ax.data.repository

import com.tiphubapps.ax.data.repository.dataSource.auth.AuthDataSource
import com.tiphubapps.ax.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) : AuthRepository {
    override fun saveToken(token: String) {
        authDataSource.saveToken(token)
    }

    override fun getToken(): String? {
        return authDataSource.getToken()
    }

    override fun getTokenFlow(): Flow<String>? {
        return authDataSource.getTokenFlow()
    }

    // Other authentication-related methods...
}