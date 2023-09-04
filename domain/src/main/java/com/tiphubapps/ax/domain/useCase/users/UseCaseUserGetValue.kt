package com.tiphubapps.ax.domain.useCase.users

import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository
import kotlinx.coroutines.flow.StateFlow
import java.io.IOException

class UseCaseUserGetValue (private val userRepository: UserRepository) {

    operator fun invoke(): UseCaseResult<StateFlow<String>> {
        //Timber.d("fetchData: Fetching data from Repository")

        return try {
            val response = userRepository.getLocalValueFlow()

            return when(response.value.isNotBlank()){
                true -> UseCaseResult.UseCaseSuccess(response)
                false -> UseCaseResult.UseCaseError(Exception("Empty Error"))
            }

        } catch (e: IOException) {
            UseCaseResult.UseCaseError(Exception("IO Error"))
        } catch (e: Exception) {
            UseCaseResult.UseCaseError(Exception("Unknown Error"))
        }
    }
}