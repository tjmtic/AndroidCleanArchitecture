package com.tiphubapps.ax.Rain.di

import com.tiphubapps.ax.data.api.UserApi
import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.repository.dataSource.UserDataSource
import com.tiphubapps.ax.data.repository.dataSourceImpl.UserRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    @Named("remote")
    fun provideUsersRemoteDataSource(userApi: UserApi, userDao: UserDao) : UserDataSource =
        UserRemoteDataSourceImpl(userApi, userDao = userDao)
}