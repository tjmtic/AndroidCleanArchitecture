package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository

class PostLoginUseCase(private val userRepository: UserRepository) {
    lateinit var username: String;
    lateinit var password: String;
    operator suspend fun invoke() = userRepository.postLogin(username, password)
}