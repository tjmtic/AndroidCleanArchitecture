package com.tiphubapps.ax.data.repository.dataSourceImpl

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tiphubapps.ax.data.api.ApiMainHeadersProvider
import com.tiphubapps.ax.data.api.LoginRequest
import com.tiphubapps.ax.data.api.UserApi
import com.tiphubapps.ax.data.db.UserDB
import com.tiphubapps.ax.data.paging.UserRemoteMediator
import com.tiphubapps.ax.data.repository.dataSource.UserRemoteDataSource
import com.tiphubapps.ax.data.entity.UserEntity
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.api.CreateSessionRequest
import com.tiphubapps.ax.data.api.GetUsersRequest
import com.tiphubapps.ax.data.api.IdRequest
import com.tiphubapps.ax.data.db.Converters
import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import com.tiphubapps.ax.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UserRemoteDataSourceImpl(private val userApi: UserApi,
                               private val userDao: UserDao,
) :
    UserDataSource {
    //private val userDao = userDB.userDao()

    val headersProvider = ApiMainHeadersProvider()
    var authToken: String = ""

    override fun setUserToken(token: String){
        authToken = token;
    }

    override suspend fun getAllUsers() : Result<List<UserEntity>> {
        val response = userApi.getAllUsers()

        response.body()?.let { array ->
            val entityList = array.mapNotNull { element ->
                Converters.userEntityFromJsonObject(element.asJsonObject)
            }
            return Result.Success(entityList)
        }

        return Result.Error(Exception("Empty Error"))
    }

    override fun getUsersFromDB(userId: Int): Flow<UserEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): Result<UserEntity>{
        val response = userApi.getCurrentUser()

        response.body()?.let {
            println("TIME123 REMOTE DATAT SOURCE RESPONSE: ${it}")
            val entity = Converters.userEntityFromJsonObject(it)
            return Result.Success(entity)
        }

        return Result.Error(Exception("Empty Error"))
    }

    override suspend fun getUserById(id: String): Result<UserEntity>{

        val body = IdRequest(id)
        val response = userApi.getUserById(request = body)


        response.body()?.let {
            val entity =
                Converters.userEntityFromJsonObject(it)

            return Result.Success(entity)
        }

        return Result.Error(Exception("Empty Error"))
    }

    override suspend fun createSessionByUsers(receiverId: String): JsonObject? {
        val d = CreateSessionRequest(receiver = receiverId, )
        val response = userApi.createSessionByUser(request = d)

        return response.body()
    }

    override suspend fun getAllUsersById(historyIds: JsonArray, contributorIds: JsonArray): Result<List<UserEntity>>{

        val body = GetUsersRequest(history = historyIds.toString(), contributors = contributorIds.toString())/*JsonObject().also{
            it.addProperty("history", historyIds.toString())
            it.addProperty("contributors", contributorIds.toString())
        }*/

        val response = userApi.getUsersByIds(request = body)

        //TODO: This ABSOLUTELY BREAKS the UI
        response.body()?.let { array ->
            val entityList = array.get("history").asJsonArray.mapNotNull { element ->
                println("TIME123 history JSON OBJECT: $element")
                Converters.userEntityFromJsonObject(element.asJsonObject)
            }
            val entityList2 = array.get("contributors").asJsonArray.mapNotNull { element ->
                Converters.userEntityFromJsonObject(element.asJsonObject)
            }
            return Result.Success(entityList.plus(entityList2))
        }

        return Result.Error(Exception("Empty Error"))
    }

    override suspend fun postLogin(email:String, password:String): JsonObject? {
        val response = userApi.postLogin(LoginRequest(email, password))
        val resp = response.body();
        return resp;
    }

    override suspend fun postLogout() {
        val response = userApi.postLogout();
        val resp = response.body();
    }
}