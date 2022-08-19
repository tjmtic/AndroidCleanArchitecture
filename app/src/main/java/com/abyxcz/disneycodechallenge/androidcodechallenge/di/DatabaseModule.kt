package com.abyxcz.disneycodechallenge.androidcodechallenge.di

import android.app.Application
import androidx.room.Room
import com.abyxcz.disneycodechallenge.data.db.UserDB
import com.abyxcz.disneycodechallenge.data.db.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(app: Application): UserDB =
        Room.databaseBuilder(app, UserDB::class.java, "user_db").fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(userDB: UserDB) : UserDao = userDB.userDao()

}