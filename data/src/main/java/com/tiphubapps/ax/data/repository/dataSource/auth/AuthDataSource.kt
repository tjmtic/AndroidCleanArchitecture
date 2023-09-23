package com.tiphubapps.ax.data.repository.dataSource.auth

import kotlinx.coroutines.flow.Flow

interface AuthDataSource {
    fun saveToken(token: String)
    fun getToken(): String?
    fun getTokenFlow(): Flow<String>?
}