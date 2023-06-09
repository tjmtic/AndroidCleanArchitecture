package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(id: String, token: String) = userRepository.getUserById(id, token)
}