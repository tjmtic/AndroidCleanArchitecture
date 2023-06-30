package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository

class GetAllUsersWithTokenUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(token: String) = userRepository.getAllUsersWithToken(token)
}