package com.farhan.tanvir.androidcleanarchitecture.di

import com.farhan.tanvir.domain.repository.UserRepository
import com.farhan.tanvir.domain.useCase.GetAllUsersUseCase
import com.farhan.tanvir.domain.useCase.GetUsersFromDBUseCase
import com.farhan.tanvir.domain.useCase.UserUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUserUseCases(userRepository: UserRepository) = UserUseCases(
        getAllUsersUseCase = GetAllUsersUseCase(userRepository = userRepository),
        getUsersFromDBUseCase = GetUsersFromDBUseCase(userRepository = userRepository)
    )
}