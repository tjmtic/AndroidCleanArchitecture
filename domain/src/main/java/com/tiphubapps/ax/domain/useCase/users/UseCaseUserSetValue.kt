package com.tiphubapps.ax.domain.useCase.users

import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException

class UseCaseUserSetValue (private val userRepository: UserRepository) {

    operator fun invoke(value: String): UseCaseResult<Boolean> {
        //Timber.d("fetchData: Fetching data from Repository")
        return try {
            userRepository.updateLocalValue(value)
            UseCaseResult.UseCaseSuccess(true)
        } catch (e: IOException) {
            UseCaseResult.UseCaseError(Exception("IO Error"))
        } catch (e: Exception) {
            UseCaseResult.UseCaseError(Exception("Unknown Error"))
        }
    }
}