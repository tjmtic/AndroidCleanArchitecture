package com.abyxcz.disneycodechallenge.domain.useCase

import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

class GetAllUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAllUsers()
}