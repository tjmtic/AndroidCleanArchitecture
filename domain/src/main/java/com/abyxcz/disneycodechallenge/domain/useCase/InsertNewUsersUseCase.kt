package com.abyxcz.disneycodechallenge.domain.useCase

import com.abyxcz.disneycodechallenge.domain.model.User
import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

class InsertNewUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userList: List<User>) = userRepository.insertNewUsers(userList)
}