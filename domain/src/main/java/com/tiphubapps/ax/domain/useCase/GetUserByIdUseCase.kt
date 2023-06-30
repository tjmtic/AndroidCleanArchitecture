package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(id: String, token: String) = userRepository.getUserById(id, token)
}