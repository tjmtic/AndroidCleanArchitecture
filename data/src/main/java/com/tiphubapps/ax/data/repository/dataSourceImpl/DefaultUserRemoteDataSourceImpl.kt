package com.tiphubapps.ax.data.repository.dataSourceImpl

import android.annotation.SuppressLint
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.api.ApiMainHeadersProvider
import com.tiphubapps.ax.data.api.UserApi
import com.tiphubapps.ax.data.db.Converters
import com.tiphubapps.ax.data.db.UserDB
import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.data.repository.dataSource.DefaultDataSource
import com.tiphubapps.ax.data.repository.dataSource.DefaultUserDataSource
import kotlinx.coroutines.delay
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


/**
 * Implementation of the data source from network.
 */
class DefaultUserRemoteDataSource internal constructor(
                                    private val userApi: UserApi,
                                    private val userDao: UserDao,
                                    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DefaultUserDataSource {

    //private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    //private var TASKS_SERVICE_DATA = LinkedHashMap<String, UserEntity>(2)

    init {
        //addUser("Build tower in Pisa", "Ground looks good, no foundation work required.")
       // addUser("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    //TODO: Remove headersProvider
    val headersProvider = ApiMainHeadersProvider()
    private val observableUsers = userDao.observeUsers()//MutableStateFlow<Result<List<UserEntity>>>(Result.Loading)//MutableLiveData<Result<List<User>>>()

    @SuppressLint("NullSafeMutableLiveData")
    override suspend fun refreshUsers() = withContext(ioDispatcher) {
        //TODO: Implement Sync / Refresh for LOCAL/REMOTE
       // observableUsers = getUsers()
    }

    override suspend fun refreshUser(userId: String) = withContext(ioDispatcher) {
        //TODO: Implement Sync / Refresh for LOCAL/REMOTE
       // refreshUsers()
    }

    override fun observeUsers(): Flow<Result<List<UserEntity>>> {
        //TODO: Does this need implementation on REMOTE?
        //return observableUsers
        return flowOf()
    }

    override fun observeUser(userId: String): Flow<Result<UserEntity>> {
        //TODO: Does this need implementation on REMOTE?
       /* return observableUsers.map { users ->
            when (users) {
                is Result.Loading -> Result.Loading
                is Error -> Error(users.exception)
                is Success -> {
                    val user = users.data.firstOrNull() { it.userId == userId }
                        ?: return@map Error(Exception("Not found"))
                    Success(user)
                }
            }
        }*/
        return flowOf()
    }

    override suspend fun getUsers(): Result<List<UserEntity>> = withContext(ioDispatcher){
        // Simulate network by delaying the execution.
        val response = userApi.getAllUsers(headersProvider.getAuthenticatedHeaders(""))//TASKS_SERVICE_DATA.values.toList()
        //delay(SERVICE_LATENCY_IN_MILLIS)

        //return Success(users)
        response.body()?.let { array ->
            val entityList = array.mapNotNull { element ->
                Converters.userEntityFromJsonObject(element.asJsonObject)
            }
            return@withContext Success(entityList)
        }

        return@withContext Error(Exception("Empty Error"))
    }

    override suspend fun getUser(userId: String): Result<UserEntity> = withContext(ioDispatcher) {
        val body = JsonObject().also{
            it.addProperty("id", userId)
        }

        val response = userApi.getUserById(body, headersProvider.getAuthenticatedHeaders(""))
        response.body()?.let {
            val entity = Converters.userEntityFromJsonObject(it)
            return@withContext Success(entity)
        }

        return@withContext Error(Exception("Empty Error"))
    }

    private fun addUser(title: String, description: String) {
        //val newUserEntity = UserEntity(title, description)
        //TASKS_SERVICE_DATA[newUserEntity.id] = newUserEntity
    }

    override suspend fun saveUser(userEntity: UserEntity) = withContext(ioDispatcher) {
        //TASKS_SERVICE_DATA[userEntity.id] = userEntity
    }

    override suspend fun completeUser(userEntity: UserEntity) = withContext(ioDispatcher) {
        //val completedUserEntity = UserEntity(userEntity.title, userEntity.description, true, userEntity.id)
        //TASKS_SERVICE_DATA[userEntity.id] = completedUserEntity
    }

    override suspend fun completeUser(userId: String) = withContext(ioDispatcher) {
        // Not required for the remote data source
    }

    override suspend fun activateUser(userEntity: UserEntity) = withContext(ioDispatcher) {
        //val activeUserEntity = UserEntity(userEntity.title, userEntity.description, false, userEntity.id)
        //TASKS_SERVICE_DATA[userEntity.id] = activeUserEntity
    }

    override suspend fun activateUser(userId: String) = withContext(ioDispatcher) {
        // Not required for the remote data source
    }

    override suspend fun clearCompletedUsers() = withContext(ioDispatcher) {
        //TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filterValues {
        //    !it.isCompleted
        //} as LinkedHashMap<String, UserEntity>
    }

    override suspend fun deleteAllUsers() = withContext(ioDispatcher) {
        //TASKS_SERVICE_DATA.clear()
    }

    override suspend fun deleteUser(userId: String) {
       // TASKS_SERVICE_DATA.remove(userId)
    }
}