package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.Result

import com.tiphubapps.ax.domain.repository.UserRepository
import com.google.gson.JsonObject
import java.io.IOException

// Use case module
class UseCaseLogin(private val userRepository: UserRepository) {

    //lateinit var username: String;
    //lateinit var password: String;
    //operator suspend fun invoke() = userRepository.postLogin(username, password)

    suspend operator fun invoke(username:String, password:String): Result<List<JsonObject>> {
        //Timber.d("fetchData: Fetching data from API")

        return try {
            ///////////////////////////////
            val response = userRepository.postLogin(username, password)
            ///////////////////////////////

            /////////THIS IS A TODO////////////////////
            if (response?.size()!! >= 0) {
                val data = listOf<JsonObject>(response!!.asJsonObject)
            ///////////////////////////////////////////
                //Timber.d("fetchData: Data fetched successfully")
                Result.Success(data ?: emptyList())
            }
            //Catch and return input exception "invalid username/password"
            //else if (){}
            else {
                //Timber.e("fetchData: Server error. Code: ${response.code()}, Message: ${response.message()}")
                Result.Error(AppError.ServerError)
            }
        } catch (e: IOException) {
            //Timber.e(e, "fetchData: Network error")
            Result.Error(AppError.NetworkError)
        } catch (e: Exception) {
            //Timber.e(e, "fetchData: Custom error")
            Result.Error(AppError.CustomError(e.message ?: "Unknown error"))
        }
    }
}