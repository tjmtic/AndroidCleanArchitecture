package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository


data class UserUseCases(
    val getCurrentUserUseCase: GetCurrentUserUseCase,
    val getCurrentUserWithTokenUseCase: GetCurrentUserWithTokenUseCase,
    val getUserByIdUseCase: GetUserByIdUseCase,
    val getUsersByIdUseCase: GetUsersByIdUseCase,
    val createSessionByUserUseCase: CreateSessionByUsersUseCase,
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getAllUsersWithTokenUseCase: GetAllUsersWithTokenUseCase,
    val getUsersFromDBUseCase: GetUsersFromDBUseCase,
    val postLoginUseCase: PostLoginUseCase,
    val useCaseLogin: UseCaseLogin
){
    fun builder (userRepository: UserRepository): UserUseCases{
        return UserUseCases(
                        getCurrentUserUseCase = GetCurrentUserUseCase(userRepository = userRepository),
                        getUserByIdUseCase = GetUserByIdUseCase(userRepository = userRepository),
                        getUsersByIdUseCase = GetUsersByIdUseCase(userRepository = userRepository),
                        createSessionByUserUseCase = CreateSessionByUsersUseCase(userRepository = userRepository),
                        getCurrentUserWithTokenUseCase = GetCurrentUserWithTokenUseCase(userRepository = userRepository),
                        getAllUsersUseCase = GetAllUsersUseCase(userRepository = userRepository),
                        getAllUsersWithTokenUseCase = GetAllUsersWithTokenUseCase(userRepository = userRepository),
                        getUsersFromDBUseCase = GetUsersFromDBUseCase(userRepository = userRepository),
                        postLoginUseCase = PostLoginUseCase(userRepository = userRepository),

                        useCaseLogin = UseCaseLogin(userRepository = userRepository))
    }
}

