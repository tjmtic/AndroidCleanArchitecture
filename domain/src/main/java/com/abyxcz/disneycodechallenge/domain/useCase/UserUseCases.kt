package com.abyxcz.disneycodechallenge.domain.useCase


data class UserUseCases(
    val insertNewUserUseCase: InsertNewUserUseCase,
    val insertNewUsersUseCase: InsertNewUsersUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getAllUsersWithReservationUseCase: GetAllUsersWithReservationUseCase,
    val getAllUsersWithoutReservationUseCase: GetAllUsersWithoutReservationUseCase,
    val selectUserUseCase: SelectUserUseCase,
    val unselectUserUseCase: UnselectUserUseCase,
    val getAllSelectedUsersUseCase: GetAllSelectedUsersUseCase,
    val deleteAllUsersUseCase: DeleteAllUsersUseCase,
)
