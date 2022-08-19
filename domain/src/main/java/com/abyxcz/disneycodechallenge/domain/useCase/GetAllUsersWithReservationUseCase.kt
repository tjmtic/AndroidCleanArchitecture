package com.abyxcz.disneycodechallenge.domain.useCase

import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

class GetAllUsersWithReservationUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAllUsersWithReservation()
}