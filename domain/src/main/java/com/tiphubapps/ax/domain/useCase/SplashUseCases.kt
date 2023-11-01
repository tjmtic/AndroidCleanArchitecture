package com.tiphubapps.ax.domain.useCase

import com.tiphubapps.ax.domain.repository.UserRepository
import com.tiphubapps.ax.domain.useCase.auth.UseCaseAuthGetToken
import com.tiphubapps.ax.domain.useCase.auth.UseCaseAuthGetTokenFlow
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserGetValue
import com.tiphubapps.ax.domain.useCase.users.UseCaseUserSetValue


data class SplashUseCases(
    val useCaseUserGetCurrentUser: GetCurrentUserUseCase,
    val useCaseAuthGetToken: UseCaseAuthGetToken,
)

