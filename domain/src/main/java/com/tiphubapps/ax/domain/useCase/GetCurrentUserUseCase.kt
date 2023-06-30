package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository

class GetCurrentUserUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke() = userRepository.getCurrentUser()
}