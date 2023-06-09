package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.AppError
import com.farhan.tanvir.domain.repository.Result

import com.farhan.tanvir.domain.repository.UserRepository
import com.google.gson.JsonObject
import java.io.IOException

// Use case module
class DataUseCase(private val userRepository: UserRepository) {

    suspend fun fetchData(): Result<List<JsonObject>> {
        //Timber.d("fetchData: Fetching data from API")

        return try {
            ///////////////////////////////
            val response = userRepository.getAllUsers()
            ///////////////////////////////

            /////////THIS IS A TODO////////////////////
            if (!response?.isEmpty!!) {
                val data = listOf<JsonObject>(response!!.asJsonObject)
            ///////////////////////////////////////////
                //Timber.d("fetchData: Data fetched successfully")
                Result.Success(data ?: emptyList())
            } else {
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