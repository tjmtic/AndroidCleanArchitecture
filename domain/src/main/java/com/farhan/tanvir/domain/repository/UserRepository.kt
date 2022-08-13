package com.farhan.tanvir.domain.repository

import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface UserRepository {
    fun getAllUsers(): Flow<PagingData<User>>
    fun getUsersFromDB(userId: Int): Flow<User>
    suspend fun postLogin(email: String, password: String): JSONObject?
}