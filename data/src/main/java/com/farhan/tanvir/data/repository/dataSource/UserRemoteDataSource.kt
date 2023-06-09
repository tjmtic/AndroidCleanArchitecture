package com.farhan.tanvir.data.repository.dataSource

import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface UserRemoteDataSource {
     suspend fun getCurrentUser(): JsonObject?
     suspend fun getUserById(d: String): JsonObject?
     suspend fun getAllUsersById(historyIds: JsonArray, contributorIds: JsonArray): JsonObject?
     suspend fun getAllUsers(): JsonArray?
     suspend fun postLogin(email:String, password:String): JsonObject?
     suspend fun postLogout()
     fun setUserToken(token: String)
}