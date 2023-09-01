package com.tiphubapps.ax.data.repository.dataSourceImpl

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import kotlinx.coroutines.flow.Flow


class UserLocalDataSourceImpl(private val userDao: UserDao) : UserDataSource {
    override fun getUsersFromDB(userId: Int): Flow<UserEntity?> = userDao.getUser(userId)
    override suspend fun getCurrentUser(): Result<UserEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserById(d: String): Result<UserEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsersById(
        historyIds: JsonArray,
        contributorIds: JsonArray
    ): Result<List<UserEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllUsers(): Result<List<UserEntity>> {
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