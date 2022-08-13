package com.farhan.tanvir.androidcleanarchitecture.di

import com.farhan.tanvir.data.api.UserApi
import com.farhan.tanvir.data.db.UserDB
import com.farhan.tanvir.data.repository.dataSource.UserRemoteDataSource
import com.farhan.tanvir.data.repository.dataSourceImpl.UserRemoteDataSourceImpl
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