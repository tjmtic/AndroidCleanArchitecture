package com.tiphubapps.ax.data.repository.dataSource.auth

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthDataSource {
    suspend fun saveToken(token: String)
    fun getToken(): String?
    fun getTokenFlow(): StateFlow<Boolean>
    //fun getTokenLiveData(): LiveData<Boolean>

}