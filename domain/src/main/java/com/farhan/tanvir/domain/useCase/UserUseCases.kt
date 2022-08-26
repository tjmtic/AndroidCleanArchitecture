package com.farhan.tanvir.domain.useCase


data class UserUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getUsersFromDBUseCase: GetUsersFromDBUseCase,
    val postLoginUseCase: PostLoginUseCase
)

interface Invokable{
    fun invoke()
}
