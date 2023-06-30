package com.tiphubapps.ax.rain.di

import com.tiphubapps.ax.data.api.UserApi
import com.tiphubapps.ax.data.db.UserDB
import com.tiphubapps.ax.data.repository.dataSource.UserRemoteDataSource
import com.tiphubapps.ax.data.repository.dataSourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    fun provideUsersRemoteDataSource(userApi: UserApi, userDB: UserDB) : UserRemoteDataSource =
        UserRemoteDataSourceImpl(userApi, userDB = userDB)
}