package com.abyxcz.disneycodechallenge.domain.useCase

import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

class GetAllSelectedUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAllSelectedUsers()
}