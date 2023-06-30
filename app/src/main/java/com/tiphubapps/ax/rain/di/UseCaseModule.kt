package com.tiphubapps.ax.rain.di

import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.*
import com.tiphubapps.ax.domain.useCase.CreateSessionByUsersUseCase
import com.tiphubapps.ax.domain.useCase.GetAllUsersUseCase
import com.tiphubapps.ax.domain.useCase.GetAllUsersWithTokenUseCase
import com.tiphubapps.ax.domain.useCase.GetCurrentUserUseCase
import com.tiphubapps.ax.domain.useCase.GetCurrentUserWithTokenUseCase
import com.tiphubapps.ax.domain.useCase.GetUserByIdUseCase
import com.tiphubapps.ax.domain.useCase.GetUsersByIdUseCase
import com.tiphubapps.ax.domain.useCase.GetUsersFromDBUseCase
import com.tiphubapps.ax.domain.useCase.PostLoginUseCase
import com.tiphubapps.ax.domain.useCase.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUserUseCases(userRepository: UserRepository) = UserUseCases(
        getCurrentUserUseCase = GetCurrentUserUseCase(userRepository = userRepository),
        getUserByIdUseCase = GetUserByIdUseCase(userRepository = userRepository),
        getUsersByIdUseCase = GetUsersByIdUseCase(userRepository = userRepository),
        createSessionByUserUseCase = CreateSessionByUsersUseCase(userRepository = userRepository),
        getCurrentUserWithTokenUseCase = GetCurrentUserWithTokenUseCase(userRepository = userRepository),
        getAllUsersUseCase = GetAllUsersUseCase(userRepository = userRepository),
        getAllUsersWithTokenUseCase = GetAllUsersWithTokenUseCase(userRepository = userRepository),
        getUsersFromDBUseCase = GetUsersFromDBUseCase(userRepository = userRepository),
        postLoginUseCase = PostLoginUseCase(userRepository = userRepository)
    )
}