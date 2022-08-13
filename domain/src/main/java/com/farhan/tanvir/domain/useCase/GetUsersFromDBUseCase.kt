package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class GetUsersFromDBUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userID: Int) = userRepository.getUsersFromDB(userID)
}