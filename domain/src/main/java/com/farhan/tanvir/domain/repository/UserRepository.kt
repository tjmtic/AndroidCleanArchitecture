package com.farhan.tanvir.domain.repository

import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface UserRepository {
    fun getCurrentToken(): String?
    suspend fun getCurrentUser(): JsonObject?
    suspend fun getCurrentUserWithToken(token: String): JsonObject?
    suspend fun getAllUsers(): JsonObject?
    suspend fun getAllUsersWithToken(token: String): JsonObject?
    fun getUsersFromDB(userId: Int): Flow<User>
    suspend fun postLogin(email: String, password: String): JsonObject?
    suspend fun logout()
}

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T : Any> {

    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}