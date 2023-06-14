package com.farhan.tanvir.data.repository.dataSourceImpl

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.farhan.tanvir.data.api.ApiMainHeadersProvider
import com.farhan.tanvir.data.api.LoginRequest
import com.farhan.tanvir.data.api.UserApi
import com.farhan.tanvir.data.db.UserDB
import com.farhan.tanvir.data.paging.UserRemoteMediator
import com.farhan.tanvir.data.repository.dataSource.UserRemoteDataSource
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class UserRemoteDataSourceImpl(private val userApi: UserApi,
                               private val userDB: UserDB, ) :
    UserRemoteDataSource {
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

    override suspend fun getCurrentUser(): JsonObject?{
       /* return flow {
            val response = userApi.getCurrentUser();

            emit(response.body());
        }*/
        val response = userApi.getCurrentUser(authedHeaders = headersProvider.getAuthenticatedHeaders(authToken))
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