package com.abyxcz.disneycodechallenge.domain.useCase

import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

class GetAllUsersWithoutReservationUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAllUsersWithoutReservation()
}