package com.tiphubapps.ax.rain.di

import com.tiphubapps.ax.data.repository.UserRepositoryImpl
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.repository.dataSource.UserRemoteDataSource
import com.tiphubapps.ax.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideUsersRepository(
        userRemoteDataSource: UserRemoteDataSource,
        userLocalDataSource: UserLocalDataSource
    ): UserRepository =
        UserRepositoryImpl(userRemoteDataSource, userLocalDataSource = userLocalDataSource)
}