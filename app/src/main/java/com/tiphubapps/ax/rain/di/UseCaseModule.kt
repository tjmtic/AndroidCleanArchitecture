package com.tiphubapps.ax.rain.di

import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.*
import com.tiphubapps.ax.domain.useCase.CreateSessionByUsersUseCase
import com.tiphubapps.ax.domain.useCase.GetAllUsersUseCase
//import com.tiphubapps.ax.domain.useCase.GetAllUsersWithTokenUseCase
import com.tiphubapps.ax.domain.useCase.GetCurrentUserUseCase
//import com.tiphubapps.ax.domain.useCase.GetCurrentUserWithTokenUseCase
import com.tiphubapps.ax.domain.useCase.GetUserByIdUseCase
import com.tiphubapps.ax.domain.useCase.GetUsersByIdUseCase
import com.tiphubapps.ax.domain.useCase.GetUsersFromDBUseCase
import com.tiphubapps.ax.domain.useCase.PostLoginUseCase
import com.tiphubapps.ax.domain.useCase.UserUseCases
import com.tiphubapps.ax.domain.useCase.auth.UseCaseAuthGetToken
import com.tiphubapps.ax.domain.useCase.auth.UseCaseAuthGetTokenFlow
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserGetValue
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserSetValue
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Named("suite")
    fun provideUserUseCases(userRepository: UserRepository) = UserUseCases(
        getCurrentUserUseCase = GetCurrentUserUseCase(userRepository = userRepository),
        getUserByIdUseCase = GetUserByIdUseCase(userRepository = userRepository),
        getUsersByIdUseCase = GetUsersByIdUseCase(userRepository = userRepository),
        createSessionByUserUseCase = CreateSessionByUsersUseCase(userRepository = userRepository),
        //getCurrentUserWithTokenUseCase = GetCurrentUserWithTokenUseCase(userRepository = userRepository),
        getAllUsersUseCase = GetAllUsersUseCase(userRepository = userRepository),
        //getAllUsersWithTokenUseCase = GetAllUsersWithTokenUseCase(userRepository = userRepository),
        getUsersFromDBUseCase = GetUsersFromDBUseCase(userRepository = userRepository),
        postLoginUseCase = PostLoginUseCase(userRepository = userRepository),

        useCaseLogin = UseCaseLogin(userRepository = userRepository)
    )

    @Provides
    @Named("login")
    fun provideUseCasesForLogin(userRepository: UserRepository) = UserUseCases(
        useCaseLogin = UseCaseLogin(userRepository = userRepository),
        useCaseUserGetValue = UseCaseUserGetValue(userRepository = userRepository) ,
        useCaseUserSetValue = UseCaseUserSetValue(userRepository = userRepository)
    )

    @Provides
    fun provideLoginUseCases(userRepository: UserRepository, authRepository: AuthRepository) = LoginUseCases(
        useCaseLogin = UseCaseLogin(userRepository = userRepository),
        useCaseUserGetValue = UseCaseUserGetValue(userRepository = userRepository) ,
        useCaseUserSetValue = UseCaseUserSetValue(userRepository = userRepository),
        useCaseAuthGetToken = UseCaseAuthGetToken(authRepository = authRepository)
    )

    @Provides
    fun provideAuthUseCases(authRepository: AuthRepository) = AuthUseCases(
        useCaseAuthGetToken = UseCaseAuthGetToken(authRepository = authRepository),
        useCaseAuthGetTokenFlow = UseCaseAuthGetTokenFlow(authRepository = authRepository)
    )
}