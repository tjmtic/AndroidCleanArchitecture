package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.model.User
import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UserRepository

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    operator suspend fun invoke(id: String): UseCaseResult<User> = userRepository.getUserById(id)
}