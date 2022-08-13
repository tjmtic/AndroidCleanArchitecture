package com.farhan.tanvir.domain.useCase


data class UserUseCases(
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getUsersFromDBUseCase: GetUsersFromDBUseCase,
    val postLoginUseCase: PostLoginUseCase
)
