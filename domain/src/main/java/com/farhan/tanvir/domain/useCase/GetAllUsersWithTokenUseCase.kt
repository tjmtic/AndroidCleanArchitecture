package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class GetAllUsersWithTokenUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(token: String) = userRepository.getAllUsersWithToken(token)
}