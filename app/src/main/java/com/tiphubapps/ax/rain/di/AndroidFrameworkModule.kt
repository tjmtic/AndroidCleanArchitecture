package com.tiphubapps.ax.rain.di

import android.content.Context
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.data.util.MainCoroutineContextProvider
import com.tiphubapps.ax.domain.repository.AndroidFrameworkRepository
import com.tiphubapps.ax.rain.util.AuthInterceptor
import com.tiphubapps.ax.rain.util.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object AndroidFrameworkModule {

    @Provides
    fun provideCoroutineContextProvider(): CoroutineContextProvider = MainCoroutineContextProvider()
    @Provides
    fun provideMyRepository(context: Context): AndroidFrameworkRepository {
        return AndroidFrameworkRepository(context)
    }

    @Provides
    //@Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager = SessionManager(context)

    @Provides
    //@Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): AuthInterceptor = AuthInterceptor(sessionManager)
}
