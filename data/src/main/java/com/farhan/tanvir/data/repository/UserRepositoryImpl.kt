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

    override suspend fun getCurrentUser(): JsonObject? =
        userRemoteDataSource.getCurrentUser()

    override fun getAllUsers() =
        userRemoteDataSource.getAllUsers()

    override fun getUsersFromDB(userId: Int): Flow<User> =
        userLocalDataSource.getUsersFromDB(userId)

    override suspend fun postLogin(email:String, password:String): JsonObject? {
        Log.d("TIME123", "ACtual;ly loging in. 444.." + email + password)
        return userRemoteDataSource.postLogin(email, password)
    }
}