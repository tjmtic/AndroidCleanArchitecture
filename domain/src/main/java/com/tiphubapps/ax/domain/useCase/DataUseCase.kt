package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.UseCaseResult

import com.tiphubapps.ax.domain.repository.UserRepository
import com.google.gson.JsonObject
import com.tiphubapps.ax.domain.model.User
import java.io.IOException

// Use case module
class DataUseCase(private val userRepository: UserRepository) {

    suspend fun fetchData(): UseCaseResult<List<User>> {
        //Timber.d("fetchData: Fetching data from API")

        return try {
            ///////////////////////////////
            val response = userRepository.getAllUsers()
            ///////////////////////////////

            /////////THIS IS A TODO////////////////////
            if (response is UseCaseResult.UseCaseSuccess) {
                //val data = listOf<JsonObject>(response.data)
            ///////////////////////////////////////////
                //Timber.d("fetchData: Data fetched successfully")
                UseCaseResult.UseCaseSuccess(response.data ?: emptyList())
            } else {
                //Timber.e("fetchData: Server error. Code: ${response.code()}, Message: ${response.message()}")
                //UseCaseResult.UseCaseError(AppError.ServerError)
                UseCaseResult.UseCaseError(Exception("Error"))
            }
        } catch (e: IOException) {
            //Timber.e(e, "fetchData: Network error")
            //UseCaseResult.UseCaseError(AppError.NetworkError)
            UseCaseResult.UseCaseError(Exception("Error"))
        } catch (e: Exception) {
            //Timber.e(e, "fetchData: Custom error")
            //UseCaseResult.UseCaseError(AppError.CustomError(e.message ?: "Unknown error"))
            UseCaseResult.UseCaseError(Exception("Error"))
        }
    }
}