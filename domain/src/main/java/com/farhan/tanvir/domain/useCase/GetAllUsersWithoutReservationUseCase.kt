package com.farhan.tanvir.domain.useCase

import com.farhan.tanvir.domain.repository.UserRepository

class GetAllUsersWithoutReservationUseCase(private val userRepository: UserRepository) {
    operator fun invoke() = userRepository.getAllUsersWithoutReservation()
}