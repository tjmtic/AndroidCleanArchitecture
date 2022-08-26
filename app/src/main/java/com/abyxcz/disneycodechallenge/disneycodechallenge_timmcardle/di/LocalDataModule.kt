package com.abyxcz.disneycodechallenge.disneycodechallenge_timmcardle.di

import com.abyxcz.disneycodechallenge.data.db.UserDao
import com.abyxcz.disneycodechallenge.data.repository.dataSource.UserLocalDataSource
import com.abyxcz.disneycodechallenge.data.repository.dataSourceImpl.UserLocalDataSourceImpl
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