package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository

class GetCurrentUserWithTokenUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(token: String) = userRepository.getCurrentUserWithToken(token)
}