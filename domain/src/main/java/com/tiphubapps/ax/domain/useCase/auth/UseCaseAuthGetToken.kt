package com.tiphubapps.ax.domain.useCase.auth

import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException

class UseCaseAuthGetToken (private val authRepository: AuthRepository) {

    operator fun invoke(): UseCaseResult<String> {
        //Timber.d("fetchData: Fetching data from Repository")

        return try {
            val response = authRepository.getToken()

            return response?.let { when(it.isNotBlank()){
                true -> UseCaseResult.UseCaseSuccess(response)
                false -> UseCaseResult.UseCaseError(Exception("Empty Error"))
            }} ?: run { UseCaseResult.UseCaseError(Exception("Empty Error")) }

        } catch (e: IOException) {
            UseCaseResult.UseCaseError(Exception("IO Error"))
        } catch (e: Exception) {
            UseCaseResult.UseCaseError(Exception("Unknown Error"))
        }
    }
}