package com.tiphubapps.ax.domain.repository;

import com.tiphubapps.ax.domain.model.User
import kotlinx.coroutines.flow.Flow


/**
 * Interface to the data layer.
 */
interface DefaultUsersRepository {

    fun observeUsers(): Flow<UseCaseResult<List<User>>>

    suspend fun getUsers(forceUpdate: Boolean = false): UseCaseResult<List<User>>

    suspend fun refreshUsers()

    fun observeUser(userId: String): Flow<UseCaseResult<User>>

    suspend fun getUser(userId: String, forceUpdate: Boolean = false): UseCaseResult<User>

    suspend fun refreshUser(userId: String)

    suspend fun saveUser(user: User)

    suspend fun completeUser(user: User)

    suspend fun completeUser(userId: String)

    suspend fun activateUser(user: User)

    suspend fun activateUser(userId: String)

    suspend fun clearCompletedUsers()

    suspend fun deleteAllUsers()

    suspend fun deleteUser(userId: String)
}


