package com.tiphubapps.ax.domain.repository;

import androidx.lifecycle.LiveData
import com.tiphubapps.ax.domain.model.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


/**
 * Interface to the data layer.
 */
interface AuthRepository {

    fun getToken(): String?

    fun getTokenFlow(): StateFlow<Boolean>
    //fun getTokenLiveData(): LiveData<Boolean>

    suspend fun saveToken(token: String)

}


