package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.repository.UserRepository

class InsertNewUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userList: List<User>) = userRepository.insertNewUsers(userList)
}