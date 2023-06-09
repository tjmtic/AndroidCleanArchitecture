package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository
import com.google.gson.JsonArray

class GetUsersByIdUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(historyIds: JsonArray, contributorIds: JsonArray, token: String) =
        userRepository.getUsersById(historyIds, contributorIds, token)
}