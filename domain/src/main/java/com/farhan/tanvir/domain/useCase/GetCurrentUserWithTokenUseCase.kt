package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class GetCurrentUserWithTokenUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(token: String) = userRepository.getCurrentUserWithToken(token)
}