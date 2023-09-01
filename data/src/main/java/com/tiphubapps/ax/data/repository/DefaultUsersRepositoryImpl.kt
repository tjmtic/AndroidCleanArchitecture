package com.tiphubapps.ax.data.repository

import com.tiphubapps.ax.data.db.Converters
import com.tiphubapps.ax.data.repository.dataSource.DefaultUserDataSource
import com.tiphubapps.ax.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import com.tiphubapps.ax.data.repository.dataSourceImpl.DefaultUserLocalDataSource
import com.tiphubapps.ax.data.repository.dataSourceImpl.DefaultUserRemoteDataSource
import com.tiphubapps.ax.domain.repository.DefaultUsersRepository

import kotlinx.coroutines.flow.Flow

import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UseCaseResult.UseCaseSuccess
import com.tiphubapps.ax.domain.repository.UseCaseResult.UseCaseError
import kotlinx.coroutines.flow.mapLatest

/**
 * Concrete implementation to load users from the data sources into a cache.
 */
class DefaultUsersRepositoryImpl(
    private val usersRemoteDataSource: DefaultUserDataSource,
    private val usersLocalDataSource: DefaultUserDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : DefaultUsersRepository {

    override suspend fun getUsers(forceUpdate: Boolean): UseCaseResult<List<User>> {
        if (forceUpdate) {
            try {
                updateUsersFromRemoteDataSource()
            } catch (ex: Exception) {
                return UseCaseError(ex)
            }
        }

        return when(val resp = usersLocalDataSource.getUsers()) {
            is Result.Loading -> UseCaseResult.Loading
            is Error -> UseCaseError(resp.exception)
            is Success -> {
                UseCaseSuccess(resp.data.map{
                    Converters.userFromUserEntity(it)
                })
            }
        }
    }

    override suspend fun refreshUsers() {
        updateUsersFromRemoteDataSource()
    }

    override fun observeUsers(): Flow<UseCaseResult<List<User>>> {
        return usersLocalDataSource.observeUsers().mapLatest {
            when (it) {
                is Result.Loading -> UseCaseResult.Loading
                is Error -> UseCaseError(it.exception)
                is Success -> {
                    UseCaseSuccess(it.data.map { userEntity ->
                        Converters.userFromUserEntity(userEntity)
                    })
                }
            }
        }
    }

    override suspend fun refreshUser(userId: String) {
        updateUserFromRemoteDataSource(userId)
    }

    private suspend fun updateUsersFromRemoteDataSource() {
        val remoteUsers = usersRemoteDataSource.getUsers()

        if (remoteUsers is Success) {
            //TODO: SYNC LOCAL/REMOTE DATA
            // Real apps might want to do a proper sync.
            usersLocalDataSource.deleteAllUsers()
            remoteUsers.data.forEach { user ->
                usersLocalDataSource.saveUser(user)
            }
        } else if (remoteUsers is Result.Error) {
            throw remoteUsers.exception
        }
    }

    override fun observeUser(userId: String): Flow<UseCaseResult<User>> {
        return usersLocalDataSource.observeUser(userId).mapLatest {
            when (it) {
                is Result.Loading -> UseCaseResult.Loading
                is Error -> UseCaseError(it.exception)
                is Success -> {
                    UseCaseSuccess(
                        Converters.userFromUserEntity(it.data)
                    )
                }
            }
        }
    }

    private suspend fun updateUserFromRemoteDataSource(userId: String) {
        val remoteUser = usersRemoteDataSource.getUser(userId)

        if (remoteUser is Success) {
            usersLocalDataSource.saveUser(remoteUser.data)
        }
    }

    /**
     * Relies on [getUsers] to fetch data and picks the user with the same ID.
     */
    override suspend fun getUser(userId: String, forceUpdate: Boolean): UseCaseResult<User> {
        if (forceUpdate) {
            updateUserFromRemoteDataSource(userId)
        }
        //return usersLocalDataSource.getUser(userId)

        return when(val resp = usersLocalDataSource.getUser(userId)) {
            is Result.Loading -> UseCaseResult.Loading
            is Error -> UseCaseError(resp.exception)
            is Success -> {
                UseCaseSuccess(Converters.userFromUserEntity(resp.data))
            }
        }
    }

    override suspend fun saveUser(user: User) {
        coroutineScope {
            launch { usersRemoteDataSource.saveUser(Converters.userEntityFromUser(user)) }
            launch { usersLocalDataSource.saveUser(Converters.userEntityFromUser(user)) }
        }
    }

    override suspend fun completeUser(user: User) {
        coroutineScope {
            launch { usersRemoteDataSource.completeUser(Converters.userEntityFromUser(user)) }
            launch { usersLocalDataSource.completeUser(Converters.userEntityFromUser(user)) }
        }
    }

    override suspend fun completeUser(userId: String) {
        withContext(ioDispatcher) {
            (getUserWithId(userId) as? Success)?.let { it ->
                completeUser(it.data)
            }
        }
    }

    override suspend fun activateUser(user: User) = withContext<Unit>(ioDispatcher) {
        coroutineScope {
            launch { usersRemoteDataSource.activateUser(Converters.userEntityFromUser(user)) }
            launch { usersLocalDataSource.activateUser(Converters.userEntityFromUser(user)) }
        }
    }

    override suspend fun activateUser(userId: String) {
        withContext(ioDispatcher) {
            (getUserWithId(userId) as? Success)?.let { it ->
                activateUser(it.data)
            }
        }
    }

    override suspend fun clearCompletedUsers() {
        coroutineScope {
            launch { usersRemoteDataSource.clearCompletedUsers() }
            launch { usersLocalDataSource.clearCompletedUsers() }
        }
    }

    override suspend fun deleteAllUsers() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { usersRemoteDataSource.deleteAllUsers() }
                launch { usersLocalDataSource.deleteAllUsers() }
            }
        }
    }

    override suspend fun deleteUser(userId: String) {
        coroutineScope {
            launch { usersRemoteDataSource.deleteUser(userId) }
            launch { usersLocalDataSource.deleteUser(userId) }
        }
    }

    private suspend fun getUserWithId(id: String): Result<User> {

        return when(val resp = usersLocalDataSource.getUser(id)) {
            is Result.Loading -> resp
            is Error -> resp
            is Success -> {
                Success(Converters.userFromUserEntity(resp.data))
            }
        }
    }
}