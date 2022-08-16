package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.repository.UserRepository

class InsertNewUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(user: User) = userRepository.insertNewUser(user)
}