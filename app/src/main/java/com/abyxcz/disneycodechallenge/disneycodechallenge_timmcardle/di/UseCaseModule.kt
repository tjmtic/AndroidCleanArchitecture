package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.di

import com.abyxcz.disneycodechallenge.domain.repository.UserRepository
import com.abyxcz.disneycodechallenge.domain.useCase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideUserUseCases(userRepository: UserRepository) = UserUseCases(
        insertNewUserUseCase = InsertNewUserUseCase(userRepository = userRepository),
        insertNewUsersUseCase = InsertNewUsersUseCase(userRepository = userRepository),
        getAllUsersUseCase = GetAllUsersUseCase(userRepository = userRepository),
        getAllUsersWithReservationUseCase = GetAllUsersWithReservationUseCase(userRepository = userRepository),
        getAllUsersWithoutReservationUseCase = GetAllUsersWithoutReservationUseCase(userRepository = userRepository),
        selectUserUseCase = SelectUserUseCase(userRepository = userRepository),
        unselectUserUseCase = UnselectUserUseCase(userRepository = userRepository),
        getAllSelectedUsersUseCase = GetAllSelectedUsersUseCase(userRepository = userRepository),
        deleteAllUsersUseCase = DeleteAllUsersUseCase(userRepository = userRepository),
    )
}