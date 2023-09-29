package com.tiphubapps.ax.Rain.di

import android.content.Context
import com.tiphubapps.ax.domain.repository.AndroidFrameworkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object AndroidFrameworkModule {
    @Provides
    fun provideMyRepository(context: Context): AndroidFrameworkRepository {
        return AndroidFrameworkRepository(context)
    }
}
