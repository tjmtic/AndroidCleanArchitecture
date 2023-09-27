package com.tiphubapps.ax.domain.useCase

import android.util.Log
import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.UseCaseResult

import com.tiphubapps.ax.domain.repository.UserRepository
import com.google.gson.JsonObject
import java.io.IOException

// Use case module
class UseCaseLogout(private val userRepository: UserRepository) {

    suspend operator fun invoke(): UseCaseResult<String> {
        //Timber.d("fetchData: Fetching data from API")

        return try {
            ///////////////////////////////
            val response = userRepository.postLogout()
            ///////////////////////////////

            /////////THIS IS A TODO////////////////////
            if (response?.size()!! >= 0) {
                val data = response.asJsonObject

                val result = data.get("token")?.let {
                    UseCaseResult.UseCaseSuccess(it.asString)
                } ?:
                UseCaseResult.UseCaseError(Exception("Server Error"))


                return result
            ///////////////////////////////////////////
                //Timber.d("fetchData: Data fetched successfully")
            }
            //Catch and return input exception "invalid username/password"
            //else if (){}
            else {
                //Timber.e("fetchData: Server error. Code: ${response.code()}, Message: ${response.message()}")
                //UseCaseResult.UseCaseError(AppError.ServerError)
                UseCaseResult.UseCaseError(Exception("Server Error"))
            }
        } catch (e: IOException) {
            //Timber.e(e, "fetchData: Network error")
            //UseCaseResult.UseCaseError(AppError.NetworkError)
            UseCaseResult.UseCaseError(Exception("Network Error"))
        } catch (e: Exception) {
            //Timber.e(e, "fetchData: Custom error")
            //Result.Error(AppError.CustomError(e.message ?: "Unknown error"))
            UseCaseResult.UseCaseError(Exception("Unknown Error"))
        }
    }
}