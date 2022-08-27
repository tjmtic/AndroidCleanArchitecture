package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getAllUsers()
}