package com.tiphubapps.ax.domain.useCase

import android.util.Log
import com.tiphubapps.ax.domain.repository.AppError
import com.tiphubapps.ax.domain.repository.UseCaseResult

import com.tiphubapps.ax.domain.repository.UserRepository
import com.google.gson.JsonObject
import java.io.IOException

// Use case module
class UseCaseLogout(private val userRepository: UserRepository) {

    suspend operator fun invoke(): UseCaseResult<Boolean> {
        //Timber.d("fetchData: Fetching data from API")

        return try {
            ///////////////////////////////
            val response = userRepository.postLogout()
            ///////////////////////////////



                return response
            ///////////////////////////////////////////
                //Timber.d("fetchData: Data fetched successfully")
          //  }
            //Catch and return input exception "invalid username/password"
            //else if (){}

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