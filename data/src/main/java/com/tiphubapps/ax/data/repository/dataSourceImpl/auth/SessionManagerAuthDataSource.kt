package com.tiphubapps.ax.data.repository.dataSourceImpl.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tiphubapps.ax.data.repository.dataSource.auth.AuthDataSource
import com.tiphubapps.ax.data.util.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SessionManagerAuthDataSource @Inject constructor(private val sessionManager: SessionManager) :
    AuthDataSource {
    override suspend fun saveToken(token: String) {
        try {
            println("SAvinGNGG USER TOKEN: ${token}")
            sessionManager.setUserToken(token)
            //updateData(token.isNotBlank() && token.isNotEmpty())
            //withContext(Dispatchers.Main) {
            _authedFlow.emit(token.isNotBlank() && token.isNotEmpty())
            //}
            println("Set Authed Flow USER TOKEN: ${token.isNotBlank() && token.isNotEmpty()}")
        }catch(e: Exception){
            println("Esceptions MESAGEE!!!!!!!${e.message}")
        }
    }

    override fun getToken(): String? {
        return sessionManager.getUserToken()
    }

    override fun getTokenFlow(): StateFlow<Boolean> {
        return authedFlow
        //return flowOf(dataLiveData)
    }

    private val _authedFlow : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val authedFlow : StateFlow<Boolean> = _authedFlow.asStateFlow()
    /*flow<Boolean> {
       // while(true){
            getToken()?.let { /*updateData(it.isNotBlank() && it.isNotEmpty());*/emit(it.isNotBlank() && it.isNotEmpty()); println("emitting authedFlow true = ${it.isNotBlank() && it.isNotEmpty()}") } ?: run{  emit(false) ; println("emitting authedFlow false")}
       //     delay(5000)
       // }
    }*/

    //private val dataLiveData = MutableLiveData<Boolean>()

    //override fun getTokenLiveData(): LiveData<Boolean> {
    //    return dataLiveData
    //}

    // When data changes, update the LiveData
    /*fun updateData(newData: Boolean) {
        println("SETTING LIVE DATA DATA1: $newData")
        dataLiveData.value = newData
        println("SETTING LIVE DATA DATA2: $newData")

    }*/
}