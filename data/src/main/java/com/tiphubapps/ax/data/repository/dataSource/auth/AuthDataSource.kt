package com.tiphubapps.ax.data.repository.dataSource.auth

interface AuthDataSource {
    fun saveToken(token: String)
    fun getToken(): String?
}