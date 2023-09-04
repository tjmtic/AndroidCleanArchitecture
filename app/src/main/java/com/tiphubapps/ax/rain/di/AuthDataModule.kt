package com.tiphubapps.ax.rain.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.repository.dataSource.auth.AuthDataSource
import com.tiphubapps.ax.data.repository.dataSourceImpl.UserLocalDataSourceImpl
import com.tiphubapps.ax.data.repository.dataSourceImpl.auth.SessionManagerAuthDataSource
import com.tiphubapps.ax.data.util.SessionManager
import com.tiphubapps.ax.domain.repository.AuthRepository
import com.tiphubapps.ax.rain.R
import com.tiphubapps.ax.rain.util.AuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object AuthDataModule {
    @Provides
    @Named("shared")
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    @Provides
    @Named("encrypted")
    fun provideEncryptedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val mainKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "TipHubSharPrefEncr",
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return sharedPreferences
    }

    @Provides
    //@Singleton
    fun provideSessionManager(@Named("shared")sharedPreferences: SharedPreferences,
                              @Named("encrypted") encryptedSharedPreferences: SharedPreferences
    ): SessionManager = SessionManager(sharedPreferences, encryptedSharedPreferences)

    @Provides
    //@Singleton
    fun provideAuthInterceptor(authRepository: AuthRepository): AuthInterceptor = AuthInterceptor(authRepository)

    @Provides
    fun provideAuthDataSource(sessionManager: SessionManager): AuthDataSource =
        SessionManagerAuthDataSource(sessionManager = sessionManager)
}