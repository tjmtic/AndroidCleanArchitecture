package com.farhan.tanvir.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.model.UserRemoteKeys

@Database(
    entities = [User::class, UserRemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userRemoteKeysDao(): UserRemoteKeysDao
}