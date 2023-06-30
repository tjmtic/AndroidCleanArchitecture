package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository
import com.google.gson.JsonArray

class GetUsersByIdUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(historyIds: JsonArray, contributorIds: JsonArray, token: String) =
        userRepository.getUsersById(historyIds, contributorIds, token)
}