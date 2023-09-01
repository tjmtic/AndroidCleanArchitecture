package com.tiphubapps.ax.domain.repository

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.tiphubapps.ax.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow


interface UserRepository {
    fun getCurrentToken(): String?
    suspend fun getCurrentUser(): UseCaseResult<User>
    suspend fun getCurrentUserWithToken(token: String): UseCaseResult<User>
    suspend fun getUserById(id:String, token: String): UseCaseResult<User>
    suspend fun getUsersById(historyIds:JsonArray, contributorIds:JsonArray, token: String): UseCaseResult<List<User>>

    suspend fun createSessionByUsers(data: JsonObject, token: String): JsonObject?
    suspend fun getAllUsers(): UseCaseResult<List<User>>
    suspend fun getAllUsersWithToken(token: String): UseCaseResult<List<User>>
    fun getUsersFromDB(userId: Int): Flow<User?>
    suspend fun postLogin(email: String, password: String): JsonObject?
    suspend fun logout(): Boolean

    fun getLocalValueFlow(): StateFlow<String>
    fun updateLocalValue(value: String)
}

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 *//*
sealed class UseCaseResult<out T> {
    data class Success<T>(val data: T) : UseCaseResult<T>()
    data class Error<T>(val error: AppError) : UseCaseResult<T>()
}*/

// Error types
sealed class AppError {
    object NetworkError : AppError()
    object ServerError : AppError()
    data class InputError(val errorMessage: String) : AppError()
    data class CustomError(val errorMessage: String) : AppError()
}