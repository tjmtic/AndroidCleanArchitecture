package com.farhan.tanvir.data.repository.dataSourceImpl

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.farhan.tanvir.data.api.LoginRequest
import com.farhan.tanvir.data.api.UserApi
import com.farhan.tanvir.data.db.UserDB
import com.farhan.tanvir.data.paging.UserRemoteMediator
import com.farhan.tanvir.data.repository.dataSource.UserRemoteDataSource
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


class UserRemoteDataSourceImpl(private val userApi: UserApi, private val userDB: UserDB) :
    UserRemoteDataSource {
    private val userDao = userDB.userDao()

    @OptIn(ExperimentalPagingApi::class)
    override  fun getAllUsers() : Flow<PagingData<User>> {
        Log.d("TIME123", "LOGGING FOR GET ALL USERS");
        val pagingSourceFactory = { userDao.getAllUsers() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = UserRemoteMediator(
                userApi,
                userDB
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override suspend fun getCurrentUser(): JsonObject?{
        val response = userApi.getCurrentUser()
        return response.body()
    }

    override suspend fun postLogin(email:String, password:String): JsonObject? {

        val response = userApi.postLogin(LoginRequest(email, password))
        Log.d("TIME123", "ACtual;ly loging in 555..." + response)
        Log.d("TIME123", "ACtual;ly loging in 555aa..." + response.body())

        return response.body()
    }
}