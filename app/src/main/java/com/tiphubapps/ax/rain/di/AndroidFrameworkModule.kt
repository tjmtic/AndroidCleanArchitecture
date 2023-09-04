package com.tiphubapps.ax.rain.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tiphubapps.ax.data.util.CoroutineContextProvider
import com.tiphubapps.ax.data.util.MainCoroutineContextProvider
import com.tiphubapps.ax.domain.repository.AndroidFrameworkRepository
import com.tiphubapps.ax.rain.util.AuthInterceptor
import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.data.util.SessionManager
import com.tiphubapps.ax.rain.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
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
}
