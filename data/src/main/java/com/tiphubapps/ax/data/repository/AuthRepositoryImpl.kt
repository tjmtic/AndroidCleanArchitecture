package com.tiphubapps.ax.data.repository

import androidx.lifecycle.LiveData
import com.tiphubapps.ax.data.repository.dataSource.auth.AuthDataSource
import com.tiphubapps.ax.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) : AuthRepository {
    override suspend fun saveToken(token: String) {
        authDataSource.saveToken(token)
    }

    override fun getToken(): String? {
        return authDataSource.getToken()
    }

    override fun getTokenFlow(): StateFlow<Boolean> {
        //return authDataSource.getTokenFlow()

        return authDataSource.getTokenFlow()
    }

   // override fun getTokenLiveData(): LiveData<Boolean> {
        //return authDataSource.getTokenFlow()

    //    return authDataSource.getTokenLiveData()
   // }

    /*private val _authedFlow = flow<Boolean> {
        println("emitting authedFlow init")
        val ld = authDataSource.getTokenLiveData()

        while(true){

            //emit(null)  //isRefreshing?
            authDataSource.getToken()?.let {
                emit(it.isNotBlank() && it.isNotEmpty());
                println("emitting authedFlow true = ${it.isNotBlank() && it.isNotEmpty()} ${this.hashCode()}")
            }?: run{
                emit(false) ;
                println("emitting authedFlow false")
            }

            delay(5000)

            //ld.observe({})
        }


    }.flowOn(Dispatchers.IO).distinctUntilChanged()
*/

    /*private val _callbackFlow = callbackFlow {
        authDataSource.getToken()?.let {
           // emit(it.isNotBlank() && it.isNotEmpty());
            println("emitting authedFlow true = ${it.isNotBlank() && it.isNotEmpty()}")
        }?: run{
           // emit(false) ;
            println("emitting authedFlow false")
        }

        // Remove the listener when the channel is closed
        awaitClose {
            //sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
        }
    }.flowOn(Dispatchers.IO)*/

    // Other authentication-related methods...
}