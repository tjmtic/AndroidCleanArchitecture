package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class GetCurrentUserUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke() = userRepository.getCurrentUser()
}