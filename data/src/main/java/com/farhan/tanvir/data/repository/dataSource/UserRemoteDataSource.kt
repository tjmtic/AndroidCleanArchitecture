package com.farhan.tanvir.data.repository.dataSource

import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject

interface UserRemoteDataSource {
     suspend fun getCurrentUser(): JsonObject?
     suspend fun getAllUsers(): JsonObject?
     suspend fun postLogin(email:String, password:String): JsonObject?
     suspend fun postLogout()
}