package com.farhan.tanvir.domain.useCase


data class UserUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val getCurrentUserWithTokenUseCase: GetCurrentUserWithTokenUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val getUsersByIdUseCase: GetUsersByIdUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getAllUsersWithTokenUseCase: GetAllUsersWithTokenUseCase,
    val getUsersFromDBUseCase: GetUsersFromDBUseCase,
    val postLoginUseCase: PostLoginUseCase
)

interface Invokable{
    fun invoke()
}
