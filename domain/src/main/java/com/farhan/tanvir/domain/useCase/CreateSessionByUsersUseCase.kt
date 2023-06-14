package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository
import com.google.gson.JsonObject

class CreateSessionByUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke( ids: JsonObject, token: String) = userRepository.createSessionByUsers(ids, token)
}