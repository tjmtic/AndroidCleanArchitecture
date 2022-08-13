package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class PostLoginUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(email: String, password: String) = userRepository.postLogin(email, password)
}