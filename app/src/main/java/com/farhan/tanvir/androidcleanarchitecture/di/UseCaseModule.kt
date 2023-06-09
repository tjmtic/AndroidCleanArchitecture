package com.farhan.tanvir.androidcleanarchitecture.di

import com.farhan.tanvir.domain.repository.UserRepository
import com.farhan.tanvir.domain.useCase.*
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
        getCurrentUserWithTokenUseCase = GetCurrentUserWithTokenUseCase(userRepository = userRepository),
        getAllUsersUseCase = GetAllUsersUseCase(userRepository = userRepository),
        getAllUsersWithTokenUseCase = GetAllUsersWithTokenUseCase(userRepository = userRepository),
        getUsersFromDBUseCase = GetUsersFromDBUseCase(userRepository = userRepository),
        postLoginUseCase = PostLoginUseCase(userRepository = userRepository)
    )
}