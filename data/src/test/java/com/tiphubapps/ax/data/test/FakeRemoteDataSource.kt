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
    override suspend fun getCurrentUser(): JsonObject? {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(d: String): JsonObject? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsersById(
        historyIds: JsonArray,
        contributorIds: JsonArray
    ): JsonObject? {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): JsonArray? {
        TODO("Not yet implemented")
    }

    override suspend fun createSessionByUsers(d: JsonObject): JsonObject? {
        TODO("Not yet implemented")
    }

    override suspend fun postLogin(email: String, password: String): JsonObject? {
        TODO("Not yet implemented")
    }

    override suspend fun postLogout() {
        TODO("Not yet implemented")
    }

    override fun setUserToken(token: String) {
        TODO("Not yet implemented")
    }


}