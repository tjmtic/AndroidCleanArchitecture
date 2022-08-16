package com.farhan.tanvir.domain.useCase


data class UserUseCases(
    val insertNewUserUseCase: InsertNewUserUseCase,
    val insertNewUsersUseCase: InsertNewUsersUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getAllUsersWithReservationUseCase: GetAllUsersWithReservationUseCase,
    val getAllUsersWithoutReservationUseCase: GetAllUsersWithoutReservationUseCase,
    val deleteAllUsersUseCase: DeleteAllUsersUseCase,
)
