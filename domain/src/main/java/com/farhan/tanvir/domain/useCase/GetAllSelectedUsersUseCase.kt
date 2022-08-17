package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.repository.UserRepository

class GetAllSelectedUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAllSelectedUsers()
}