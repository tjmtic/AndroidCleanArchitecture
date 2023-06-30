package com.tiphubapps.ax.domain.useCase


data class UserUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val getCurrentUserWithTokenUseCase: GetCurrentUserWithTokenUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val getUsersByIdUseCase: GetUsersByIdUseCase,
    val createSessionByUserUseCase: CreateSessionByUsersUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getAllUsersWithTokenUseCase: GetAllUsersWithTokenUseCase,
    val getUsersFromDBUseCase: GetUsersFromDBUseCase,
    val postLoginUseCase: PostLoginUseCase
)

interface Invokable{
    fun invoke()
}
