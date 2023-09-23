package com.tiphubapps.ax.domain.useCase.auth

import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException

class UseCaseAuthGetTokenFlow (private val authRepository: AuthRepository) {

    operator fun invoke(): UseCaseResult<Flow<String>> {
        //Timber.d("fetchData: Fetching data from Repository")

        return try {
            val response = authRepository.getTokenFlow()

            return response?.let {
                UseCaseResult.UseCaseSuccess(response)
                //false -> UseCaseResult.UseCaseError(Exception("Empty Error"))
            } ?: run { UseCaseResult.UseCaseError(Exception("Empty Error")) }

        } catch (e: IOException) {
            UseCaseResult.UseCaseError(Exception("IO Error"))
        } catch (e: Exception) {
            UseCaseResult.UseCaseError(Exception("Unknown Error"))
        }
    }
}