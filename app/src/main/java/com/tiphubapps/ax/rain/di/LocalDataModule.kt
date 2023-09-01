package com.tiphubapps.ax.rain.di

import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.data.repository.dataSourceImpl.UserLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    @Named("local")
    fun provideLocalDataSource(userDao: UserDao): UserDataSource =
        UserLocalDataSourceImpl(userDao = userDao)
}