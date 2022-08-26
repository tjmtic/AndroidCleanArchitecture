package com.farhan.tanvir.data.repository

import android.util.Log
import com.farhan.tanvir.data.repository.dataSource.UserLocalDataSource
import com.farhan.tanvir.data.repository.dataSource.UserRemoteDataSource
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.repository.UserRepository
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) :
    UserRepository {
    //In-Memory Cache
    var token: String?;

    var currentUser : String?;

    init {
        currentUser = null;
        token = null;
    }

    val isLoggedIn: Boolean
        get() = currentUser != null

    override suspend fun getCurrentUser(): JsonObject? =
        userRemoteDataSource.getCurrentUser()

    override fun getAllUsers() =
        userRemoteDataSource.getAllUsers()

    override fun getUsersFromDB(userId: Int): Flow<User> =
        userLocalDataSource.getUsersFromDB(userId)

    override suspend fun postLogin(email:String, password:String): JsonObject? {
        Log.d("TIME123", "ACtual;ly loging in. 444.." + email + password)
        //return userRemoteDataSource.postLogin(email, password)


        val result = userRemoteDataSource.postLogin(email, password)

        /*if (result is Result.Success) {
            setLoggedInUser(result.data)
        }*/
        result?.get("token")?.let{
            this.token = it.asString;
            Log.d("TIME123", "ACtual;ly loging in. 666.." + token)
            Log.d("TIME123", "ACtual;ly loging in. 777.." + getCurrentToken())
            //setLoggedInUser(result.data)
        }

        return result
    }

    override fun getCurrentToken(): String?{
        return token;
    }

    private fun setLoggedInUser(loggedInUserToken: String) {
        this.token = loggedInUserToken
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore

        //set application token

    }
}