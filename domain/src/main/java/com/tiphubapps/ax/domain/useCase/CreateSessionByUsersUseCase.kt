package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository
import com.google.gson.JsonObject

class CreateSessionByUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke( ids: String ) = userRepository.createSessionByUsers(ids)
}