package com.farhan.tanvir.domain.repository

import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface UserRepository {
    fun getCurrentToken(): String?
    suspend fun getCurrentUser(): JsonObject?
    suspend fun getCurrentUserWithToken(token: String): JsonObject?
    suspend fun getUserById(id:String, token: String): JsonObject?
    suspend fun getUsersById(historyIds:JsonArray, contributorIds:JsonArray, token: String): JsonObject?

    suspend fun createSessionByUsers(data: JsonObject, token: String): JsonObject?
    suspend fun getAllUsers(): JsonArray?
    suspend fun getAllUsersWithToken(token: String): JsonArray?
    fun getUsersFromDB(userId: Int): Flow<User>
    suspend fun postLogin(email: String, password: String): JsonObject?
    suspend fun logout()
}

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: AppError) : Result<T>()
}

// Error types
sealed class AppError {
    object NetworkError : AppError()
    object ServerError : AppError()
    data class InputError(val errorMessage: String) : AppError()
    data class CustomError(val errorMessage: String) : AppError()
}