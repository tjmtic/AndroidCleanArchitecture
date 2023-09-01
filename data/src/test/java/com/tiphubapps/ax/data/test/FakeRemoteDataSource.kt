package com.tiphubapps.ax.data.test

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import com.tiphubapps.ax.data.repository.dataSource.UserRemoteDataSource


class FakeRemoteDataSource(var users: MutableList<UserEntity>? = mutableListOf())  :  UserRemoteDataSource {
    override suspend fun getCurrentUser(): Result<UserEntity> {
        users?.firstOrNull() { return Success((it)) }
        return Error(
            Exception("Tasks not found")
        )
    }

    override suspend fun getUserById(d: String): Result<UserEntity> {
        users?.map{ return Success((it)) }
        return Error(
            Exception("Tasks not found")
        )
    }

    override suspend fun getAllUsersById(
        historyIds: JsonArray,
        contributorIds: JsonArray
    ): Result<List<UserEntity>> {
        users?.let { return Success(ArrayList(it)) }
        return Error(
            Exception("Tasks not found")
        )
    }

    override suspend fun getAllUsers(): Result<List<UserEntity>>  {
        users?.let { return Success(ArrayList(it)) }
        return Error(
            Exception("Tasks not found")
        )
    }

    override suspend fun createSessionByUsers(d: JsonObject): JsonObject? {
        return JsonObject().also{
            it.addProperty("_id", "1")
            it.addProperty("previous", -1)
        }
    }

    override suspend fun postLogin(email: String, password: String): JsonObject? {
        return JsonObject().also {
            it.addProperty("username","test name")
            it.addProperty("token","test token")
        }
    }

    override suspend fun postLogout() {
        TODO("Not yet implemented")
    }

    override fun setUserToken(token: String) {
    }


}