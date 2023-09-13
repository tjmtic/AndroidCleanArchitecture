package com.tiphubapps.ax.rain.di

import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.data.util.WebSocketManager
import com.tiphubapps.ax.data.util.WebSocketManagerImpl
import com.tiphubapps.ax.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebSocketModule {

    @Provides
    fun provideWebSocketManager(authRepository: AuthRepository,
                                okHttpClient: OkHttpClient,
                                coroutineContextProvider: CoroutineContextProvider): WebSocketManager
    = WebSocketManagerImpl(authRepository = authRepository, client = okHttpClient, coroutineContextProvider)

}