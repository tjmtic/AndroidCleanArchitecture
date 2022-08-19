package com.abyxcz.disneycodechallenge.domain.useCase

import com.abyxcz.disneycodechallenge.domain.model.User
import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

class UnselectUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(user: User) = userRepository.unselectUser(user)
}