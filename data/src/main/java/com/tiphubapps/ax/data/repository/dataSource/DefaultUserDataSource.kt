package com.tiphubapps.ax.data.repository.dataSource

import com.tiphubapps.ax.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow


/**
 * Main entry point for accessing users data.
 */
interface DefaultUserDataSource {


    fun observeUsers(): Flow<Result<List<UserEntity>>>

    suspend fun getUsers(): Result<List<UserEntity>>

    suspend fun refreshUsers()

    fun observeUser(userId: String): Flow<Result<UserEntity>>

    suspend fun getUser(userId: String): Result<UserEntity>

    suspend fun refreshUser(userId: String)

    suspend fun saveUser(user: UserEntity)

    suspend fun completeUser(user: UserEntity)

    suspend fun completeUser(userId: String)

    suspend fun activateUser(user: UserEntity)

    suspend fun activateUser(userId: String)

    suspend fun clearCompletedUsers()

    suspend fun deleteAllUsers()

    suspend fun deleteUser(userId: String)
}
