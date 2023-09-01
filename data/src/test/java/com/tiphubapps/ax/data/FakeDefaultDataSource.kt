package com.tiphubapps.ax.data

import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.data.repository.dataSource.DefaultUserDataSource
import com.tiphubapps.ax.data.repository.dataSource.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class FakeDefaultDataSource(var users: MutableList<UserEntity> = mutableListOf()) : DefaultUserDataSource {
    override fun observeUsers(): Flow<Result<List<UserEntity>>> {
        return flowOf(Result.Success(ArrayList(users)))
    }

    override suspend fun getUsers(): Result<List<UserEntity>> {
        return Result.Success(ArrayList(users))
    }

    override suspend fun refreshUsers() {
        //Not Implemented
    }

    override fun observeUser(userId: String): Flow<Result<UserEntity>> {
        return flowOf(Result.Success((users.first{it.userId === userId})))
    }

    override suspend fun getUser(userId: String): Result<UserEntity> {
        return Result.Success((users.first(){it.userId === userId}))
    }

    override suspend fun refreshUser(userId: String) {
        //Not Implemented
    }

    override suspend fun saveUser(user: UserEntity) {
        //Not Implemented
        users.add(user)
    }

    override suspend fun completeUser(user: UserEntity) {
        //Not Implemented
    }

    override suspend fun completeUser(userId: String) {
        //Not Implemented
    }

    override suspend fun activateUser(user: UserEntity) {
        //Not Implemented??
    }

    override suspend fun activateUser(userId: String) {
        //Not Implemented??
    }

    override suspend fun clearCompletedUsers() {
        //Not Implemented
    }

    override suspend fun deleteAllUsers() {
        users.clear()
    }

    override suspend fun deleteUser(userId: String) {
        users = users.filter{it.userId != userId }.toMutableList()
    }
}