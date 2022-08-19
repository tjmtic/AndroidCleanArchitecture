package com.abyxcz.disneycodechallenge.domain.useCase

import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

class DeleteAllUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.deleteAllUsers()
}