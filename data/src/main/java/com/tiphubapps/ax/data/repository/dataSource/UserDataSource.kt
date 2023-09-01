package com.tiphubapps.ax.data.repository.dataSource

import androidx.paging.PagingData
import com.tiphubapps.ax.domain.model.User
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tiphubapps.ax.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface UserDataSource {
     fun getUsersFromDB(userId : Int): Flow<UserEntity?>
     suspend fun getCurrentUser(): Result<UserEntity>
     suspend fun getUserById(d: String): Result<UserEntity>
     suspend fun getAllUsersById(historyIds: JsonArray, contributorIds: JsonArray): Result<List<UserEntity>>
     suspend fun getAllUsers(): Result<List<UserEntity>>
     suspend fun createSessionByUsers(d: JsonObject): JsonObject?
     suspend fun postLogin(email:String, password:String): JsonObject?
     suspend fun postLogout()
     fun setUserToken(token: String)
}