package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository

class GetUsersFromDBUseCase(private val userRepository: UserRepository) {
    operator fun invoke(userID: Int) = userRepository.getUsersFromDB(userID)
}