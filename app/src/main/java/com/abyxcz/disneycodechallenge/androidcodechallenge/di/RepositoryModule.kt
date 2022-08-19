package com.abyxcz.disneycodechallenge.androidcodechallenge.di

import com.abyxcz.disneycodechallenge.data.repository.UserRepositoryImpl
import com.abyxcz.disneycodechallenge.data.repository.dataSource.UserLocalDataSource
import com.abyxcz.disneycodechallenge.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUsersRepository(
        userLocalDataSource: UserLocalDataSource
    ): UserRepository =
        UserRepositoryImpl(userLocalDataSource = userLocalDataSource)
}