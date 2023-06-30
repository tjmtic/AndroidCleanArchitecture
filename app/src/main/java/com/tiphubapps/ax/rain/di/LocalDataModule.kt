package com.tiphubapps.ax.rain.di

import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.repository.dataSourceImpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun provideLocalDataSource(userDao: UserDao): UserLocalDataSource =
        UserLocalDataSourceImpl(userDao = userDao)
}