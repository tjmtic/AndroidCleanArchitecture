package com.tiphubapps.ax.data.repository.dataSourceImpl

import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.entity.UserEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import com.tiphubapps.ax.data.repository.dataSource.DefaultDataSource
import com.tiphubapps.ax.data.repository.dataSource.DefaultUserDataSource
import kotlinx.coroutines.flow.Flow


/**
 * Concrete implementation of a data source as a db.
 */
class DefaultUserLocalDataSource internal constructor(
    private val UsersDao: UserDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DefaultUserDataSource {

    override fun observeUsers(): Flow<Result<List<UserEntity>>> {
        return UsersDao.observeUsers().map {
            Success(it)
        }
    }

    override fun observeUser(userId: String): Flow<Result<UserEntity>> {
        return UsersDao.observeUserById(userId).map {
            Success(it)
        }
    }

    override suspend fun refreshUser(userId: String) {
        //NO-OP
    }

    override suspend fun refreshUsers() {
        //NO-OP
    }

    override suspend fun getUsers(): Result<List<UserEntity>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(UsersDao.getUsers())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getUser(userId: String): Result<UserEntity> = withContext(ioDispatcher) {
        try {
            val user = UsersDao.getUserById(userId)
            if (user != null) {
                return@withContext Success(user)
            } else {
                return@withContext Error(Exception("User not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveUser(user: UserEntity) = withContext(ioDispatcher) {
        UsersDao.insertUser(user)
    }

    override suspend fun completeUser(user: UserEntity) = withContext(ioDispatcher) {
        //UsersDao.updateCompleted(UserEntity.id, true)
        //Not Implemented
    }

    override suspend fun completeUser(userId: String) {
        //UsersDao.updateCompleted(UserId, true)
        //Not Implemented
    }

    override suspend fun activateUser(user: UserEntity) = withContext(ioDispatcher) {
        //UsersDao.updateCompleted(UserEntity.id, false)
        //Not Implemented
    }

    override suspend fun activateUser(userId: String) {
        //UsersDao.updateCompleted(UserId, false)
        //Not Implemented
    }

    override suspend fun clearCompletedUsers() = withContext<Unit>(ioDispatcher) {
        //UsersDao.deleteCompletedUsers()
        //Not Implemented
    }

    override suspend fun deleteAllUsers() = withContext(ioDispatcher) {
        UsersDao.deleteUsers()
    }

    override suspend fun deleteUser(userId: String) = withContext<Unit>(ioDispatcher) {
        UsersDao.deleteUserById(userId)
    }
}