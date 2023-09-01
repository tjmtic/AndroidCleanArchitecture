package com.tiphubapps.ax.data.repository.dataSourceImpl

import android.annotation.SuppressLint
import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.data.repository.dataSource.DefaultDataSource
import com.tiphubapps.ax.data.repository.dataSource.DefaultUserDataSource
import kotlinx.coroutines.delay
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map


/**
 * Implementation of the data source that adds a latency simulating network.
 */
object DefaultUserRemoteDataSource : DefaultUserDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, UserEntity>(2)

    init {
        addUser("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addUser("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    private val observableUsers = MutableStateFlow<Result<List<UserEntity>>>(Result.Loading)//MutableLiveData<Result<List<User>>>()

    @SuppressLint("NullSafeMutableLiveData")
    override suspend fun refreshUsers() {
        observableUsers.value = getUsers()
    }

    override suspend fun refreshUser(userId: String) {
        refreshUsers()
    }

    override fun observeUsers(): Flow<Result<List<UserEntity>>> {
        return observableUsers
    }

    override fun observeUser(userId: String): Flow<Result<UserEntity>> {
        return observableUsers.map { users ->
            when (users) {
                is Result.Loading -> Result.Loading
                is Error -> Error(users.exception)
                is Success -> {
                    val user = users.data.firstOrNull() { it.userId == userId }
                        ?: return@map Error(Exception("Not found"))
                    Success(user)
                }
            }
        }
    }

    override suspend fun getUsers(): Result<List<UserEntity>> {
        // Simulate network by delaying the execution.
        val users = TASKS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Success(users)
    }

    override suspend fun getUser(userId: String): Result<UserEntity> {
        // Simulate network by delaying the execution.
        delay(SERVICE_LATENCY_IN_MILLIS)
        TASKS_SERVICE_DATA[userId]?.let {
            return Success(it)
        }
        return Error(Exception("User not found"))
    }

    private fun addUser(title: String, description: String) {
        //val newUserEntity = UserEntity(title, description)
        //TASKS_SERVICE_DATA[newUserEntity.id] = newUserEntity
    }

    override suspend fun saveUser(userEntity: UserEntity) {
        //TASKS_SERVICE_DATA[userEntity.id] = userEntity
    }

    override suspend fun completeUser(userEntity: UserEntity) {
        //val completedUserEntity = UserEntity(userEntity.title, userEntity.description, true, userEntity.id)
        //TASKS_SERVICE_DATA[userEntity.id] = completedUserEntity
    }

    override suspend fun completeUser(userId: String) {
        // Not required for the remote data source
    }

    override suspend fun activateUser(userEntity: UserEntity) {
        //val activeUserEntity = UserEntity(userEntity.title, userEntity.description, false, userEntity.id)
        //TASKS_SERVICE_DATA[userEntity.id] = activeUserEntity
    }

    override suspend fun activateUser(userId: String) {
        // Not required for the remote data source
    }

    override suspend fun clearCompletedUsers() {
        //TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filterValues {
        //    !it.isCompleted
        //} as LinkedHashMap<String, UserEntity>
    }

    override suspend fun deleteAllUsers() {
        //TASKS_SERVICE_DATA.clear()
    }

    override suspend fun deleteUser(userId: String) {
       // TASKS_SERVICE_DATA.remove(userId)
    }
}