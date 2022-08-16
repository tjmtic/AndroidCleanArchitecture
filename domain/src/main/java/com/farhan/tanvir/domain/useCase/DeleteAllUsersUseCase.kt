package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class DeleteAllUsersUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.deleteAllUsers()
}