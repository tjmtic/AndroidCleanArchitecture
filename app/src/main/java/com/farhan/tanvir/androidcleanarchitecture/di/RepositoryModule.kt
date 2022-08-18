package com.farhan.tanvir.androidcleanarchitecture.di

import com.farhan.tanvir.data.repository.UserRepositoryImpl
import com.farhan.tanvir.data.repository.dataSource.UserLocalDataSource
import com.farhan.tanvir.domain.repository.UserRepository
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