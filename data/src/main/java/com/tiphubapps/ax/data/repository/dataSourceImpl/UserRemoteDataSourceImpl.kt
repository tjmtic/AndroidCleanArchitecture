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
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UserRemoteDataSourceImpl(private val userApi: UserApi,
                               private val userDB: UserDB, ) :
    UserDataSource {
    private val userDao = userDB.userDao()

    val headersProvider = ApiMainHeadersProvider()
    var authToken: String = ""

    override fun setUserToken(token: String){
        authToken = token;
    }

    override suspend fun getAllUsers() : JsonArray? {
        /*Log.d("TIME123", "LOGGING FOR GET ALL USERS");
        val pagingSourceFactory = { userDao.getAllUsers() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = UserRemoteMediator(
                userApi,
                userDB
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow*/
        Log.d("TIME123","GETTING ALL USERS 3");

        val response = userApi.getAllUsers(authedHeaders = headersProvider.getAuthenticatedHeaders(authToken))

        Log.d("TIME123","GETTING ALL USERS 4");

        response?.let{
            Log.d("TIME123", it.body().toString())
            Log.d("TIME123","GETTING ALL USERS 5");

        }


        return response.body()
    }

    override fun getUsersFromDB(userId: Int): Flow<UserEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentUser(): JsonObject?{
       /* return flow {
            val response = userApi.getCurrentUser();

            emit(response.body());
        }*/
        //val response = userApi.getCurrentUser(authedHeaders = headersProvider.getAuthenticatedHeaders(authToken))
        val response = userApi.getCurrentUser()
        //Log.d("TIME123", "Got Response with headers ${headersProvider.getAuthenticatedHeaders(authToken)}")
        Log.d("TIME123", "Got Response with authtoken ${authToken}")
        Log.d("TIME123", "Got Response with authtoken ${response}")
        return response.body()
    }

    override suspend fun getUserById(id: String): JsonObject?{
        /* return flow {
             val response = userApi.getCurrentUser();

             emit(response.body());
         }*/
        val body = JsonObject().also{
            it.addProperty("id", id)
        }
        val response = userApi.getUserById(id = body, authedHeaders = headersProvider.getAuthenticatedHeaders(authToken))
        Log.d("TIME123", "RAW RESPONSE 1: " + response.toString())
        return response.body()
    }

    override suspend fun createSessionByUsers(d: JsonObject): JsonObject? {
       // TODO("Not yet implemented")
        /* return flow {
             val response = userApi.getCurrentUser();

             emit(response.body());
         }*/
        val body = JsonObject().also{
          //  it.addProperty("id", d)
        }
        val response = userApi.createSessionByUser(data = d, authedHeaders = headersProvider.getAuthenticatedHeaders(authToken))
        Log.d("TIME123", "RAW RESPONSE 1: " + response.toString())
        return response.body()
    }

    override suspend fun getAllUsersById(historyIds: JsonArray, contributorIds: JsonArray): JsonObject?{
        /* return flow {
             val response = userApi.getCurrentUser();

             emit(response.body());
         }*/
        val body = JsonObject().also{
            it.addProperty("history", historyIds.toString())
            it.addProperty("contributors", contributorIds.toString())
        }

        Log.d("TIME123", "Checkming BODY:"+body)

        val response = userApi.getUsersByIds(history = body, authedHeaders = headersProvider.getAuthenticatedHeaders(authToken))

        Log.d("TIME123", "Checkming BODY response:"+ response)
        return response.body()
    }

    override suspend fun postLogin(email:String, password:String): JsonObject? {
        Log.d("TIME123", "ACtual;ly loging in aaa posting 555..." + email + password)


        val response = userApi.postLogin(LoginRequest(email, password))
        Log.d("TIME123", "ACtual;ly loging in 555..." + response)
        Log.d("TIME123", "ACtual;ly loging in 555aa..." + response.body())

        val resp = response.body();
        return resp;


        /*resp?.get("token")?.let{
            return Result.Success(resp)
        }

        return Result.Error(Exception("Bad Login"))*/
    }

    override suspend fun postLogout() {

        val response = userApi.postLogout();
        Log.d("TIME123", "ACtual;ly loging out 555..." + response)
        Log.d("TIME123", "ACtual;ly loging out 555aa..." + response.body())

        val resp = response.body();
       // return resp;


        /*resp?.get("token")?.let{
            return Result.Success(resp)
        }

        return Result.Error(Exception("Bad Login"))*/
    }
}