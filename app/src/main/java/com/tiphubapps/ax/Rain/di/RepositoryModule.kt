package com.tiphubapps.ax.Rain.di

import com.tiphubapps.ax.data.repository.AuthRepositoryImpl
import com.tiphubapps.ax.data.repository.UserRepositoryImpl
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import com.tiphubapps.ax.data.repository.dataSource.auth.AuthDataSource
import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUsersRepository(
        @Named("remote") userRemoteDataSource: UserDataSource,
        @Named("local") userLocalDataSource: UserDataSource,
        authRepository: AuthRepository
    ): UserRepository =
        UserRepositoryImpl(userRemoteDataSource = userRemoteDataSource, userLocalDataSource = userLocalDataSource, authRepository = authRepository)

    @Provides
    @Singleton
    fun provideAuthRepository(
        authDataSource: AuthDataSource,
    ): AuthRepository =
        AuthRepositoryImpl(authDataSource = authDataSource)
}