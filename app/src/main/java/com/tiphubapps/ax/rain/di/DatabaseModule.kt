package com.tiphubapps.ax.rain.di

import android.app.Application
import androidx.room.Room
import com.tiphubapps.ax.data.db.UserDB
import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.db.UserRemoteKeysDao
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

    //@Provides
    //fun provideUserDaoEncrypted(@ApplicationContext context: Context) : UserDao = UserDB.getInstance(context, "1234").userDao()

    @Provides
    fun provideUserRemoteKeysDao(userDB: UserDB) : UserRemoteKeysDao = userDB.userRemoteKeysDao()
}