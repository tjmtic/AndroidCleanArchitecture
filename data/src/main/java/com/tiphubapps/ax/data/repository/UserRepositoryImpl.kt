package com.tiphubapps.ax.data.repository

import android.util.Log
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.repository.dataSource.UserRemoteDataSource
import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.domain.repository.UserRepository
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.db.Converters
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import com.tiphubapps.ax.data.repository.dataSource.succeeded
import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.domain.repository.UseCaseResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class UserRepositoryImpl(
    private val userRemoteDataSource: UserDataSource,
    private val userLocalDataSource: UserDataSource,
    private val authRepository: AuthRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
   // val authToken: String
) :
    UserRepository {
    //In-Memory Cache
    var token: String?;

    var currentUser : String?;

    // MutableStateFlow to hold and update the local value
    private val _localValue = MutableStateFlow("Initial Value")
    // Expose the value as a StateFlow (immutable view)
    val localValue: StateFlow<String> = _localValue

    init {
        currentUser = null;
        token = null;
    }

    val isLoggedIn: Boolean
        get() = currentUser != null



    override fun updateLocalValue(value: String) {
        _localValue.value = value
    }

    override suspend fun getCurrentUser(): UseCaseResult<User> {
        return return withContext(ioDispatcher) {
            val re = userRemoteDataSource.getCurrentUser()

            return@withContext when (re) {
                is Result.Loading -> UseCaseResult.Loading
                is Result.Error -> UseCaseResult.UseCaseError(re.exception)
                is Result.Success -> {
                    UseCaseResult.UseCaseSuccess(
                        Converters.userFromUserEntity(re.data)
                    )
                }
            }
        }
    }

    override suspend fun getUserById(id: String): UseCaseResult<User> {
        //userRemoteDataSource.setUserToken(token)
        //return userRemoteDataSource.getUserById(id)

        val re = userRemoteDataSource.getUserById(id)

        return when (re) {
            is Result.Loading -> UseCaseResult.Loading
            is Result.Error -> UseCaseResult.UseCaseError(re.exception)
            is Result.Success -> {
                UseCaseResult.UseCaseSuccess(
                    Converters.userFromUserEntity(re.data)
                )
            }
        }
    }
    override suspend fun getUsersById(historyIds: JsonArray, contributorIds: JsonArray): UseCaseResult<List<User>> {
        //userRemoteDataSource.setUserToken(token)
        //return userRemoteDataSource.getAllUsersById(historyIds, contributorIds)

        val re =  userRemoteDataSource.getAllUsersById(historyIds, contributorIds)

        return when (re) {
            is Result.Loading -> UseCaseResult.Loading
            is Result.Error -> UseCaseResult.UseCaseError(re.exception)
            is Result.Success -> {
                UseCaseResult.UseCaseSuccess(re.data.map { itemEntity ->
                    Converters.userFromUserEntity(itemEntity)
                })
            }
        }
    }

    override suspend fun createSessionByUsers(receiverId: String): JsonObject? {
        //userRemoteDataSource.setUserToken(token)
        return userRemoteDataSource.createSessionByUsers(receiverId)
    }

    override suspend fun getAllUsers() : UseCaseResult<List<User>> {
        //TODO: CONVETER for Result to UseCaseResult
        //TODO: CONVERTER for MULTIPLE SUBSTITUTION
        // ... is that what converters are really for?
        // Separate this functionality from TypeConverter.

        val re = userRemoteDataSource.getAllUsers()
        return when (re) {
            is Result.Loading -> UseCaseResult.Loading
            is Result.Error -> UseCaseResult.UseCaseError(re.exception)
            is Result.Success -> {
                UseCaseResult.UseCaseSuccess(re.data.map { itemEntity ->
                    Converters.userFromUserEntity(itemEntity)
                })
            }
        }
    }


    override fun getUsersFromDB(userId: Int): Flow<User?> {
        val baseUser = userLocalDataSource.getUsersFromDB(userId)

        //TODO:
        // NEED DTO OBJECT FROM DATA > DOMAIN
        // ADD A CONVERTER!
        //Cool.
        return baseUser.mapLatest {
            it?.let { it1 -> Converters.userFromUserEntity(it1) }
        }
    }

    override suspend fun postLogin(email:String, password:String): JsonObject? {
        //Log.d("TIME123", "ACtual;ly loging in. 444.." + email + password)
        //return userRemoteDataSource.postLogin(email, password)

        return withContext(ioDispatcher) {
            coroutineScope {
                //launch {
                    val result = userRemoteDataSource.postLogin(email, password)
                    //Log.d("TIME123", "ACtual;ly loging in. 444xxx.." + result.toString())

                    /*if (result is Result.Success) {
            setLoggedInUser(result.data)
        }*/
                    result?.get("token")?.let {
                        //this.token = it.asString;
                        setCurrentToken(it.asString)
                        //Log.d("TIME123", "ACtual;ly loging in. 666.." + token)
                        //Log.d("TIME123", "ACtual;ly loging in. 777.." + getCurrentToken())
                        setLoggedInUser(it.asString)

                        updateLocalValue(it.asString)

                        authRepository.saveToken(it.asString)
                    }

                    //TODO: Investigate this method
                //return@withContext result
                return@coroutineScope result
              //  }
        }
        }
    }

    fun setCurrentToken(value: String){
        this.token = value
    }
    override fun getCurrentToken(): String?{
        return token;
    }

    override fun getLocalValueFlow(): StateFlow<String>{
        return localValue
    }

    private fun setLoggedInUser(loggedInUserToken: String) {
        this.token = loggedInUserToken
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore

        //set application token


    }
    override suspend fun postLogout() : UseCaseResult<Boolean> {
        authRepository.saveToken("")
        return UseCaseResult.UseCaseSuccess(true)
    }

    override suspend fun logout() : Boolean{

        //val result = userRemoteDataSource.postLogout();

        /*if (result is Result.Success) {
            setLoggedInUser()
        }*/

        /*result?.get("status")?.let{
            this.token = null;
            Log.d("TIME123", "ACtual;ly loging in. 666.." + token)
            Log.d("TIME123", "ACtual;ly loging in. 777.." + getCurrentToken())
            //setLoggedInUser(result.data)
        }*/

        token = null
        currentUser = null

        authRepository.saveToken("")

        return true
    }
}