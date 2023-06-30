package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getAllUsers()
}