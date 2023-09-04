package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserGetValue
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserSetValue


data class UserUseCases(
    //0.1
    val getCurrentUserUseCase: GetCurrentUserUseCase? = null,
    val getCurrentUserWithTokenUseCase: GetCurrentUserWithTokenUseCase? = null,
    val getUserByIdUseCase: GetUserByIdUseCase? = null,
    val getUsersByIdUseCase: GetUsersByIdUseCase? = null,
    val createSessionByUserUseCase: CreateSessionByUsersUseCase? = null,
    val getAllUsersUseCase: GetAllUsersUseCase? = null,
    val getAllUsersWithTokenUseCase: GetAllUsersWithTokenUseCase? = null,
    val getUsersFromDBUseCase: GetUsersFromDBUseCase? = null,
    val postLoginUseCase: PostLoginUseCase? = null,

    //1.0
    val useCaseLogin: UseCaseLogin? = null,
    val useCaseUserGetValue: UseCaseUserGetValue? = null,
    val useCaseUserSetValue: UseCaseUserSetValue? = null,
)

