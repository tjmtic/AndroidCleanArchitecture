package com.farhan.tanvir.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farhan.tanvir.domain.model.User

@Database(
    entities = [User::class],
    version = 4,
    exportSchema = false
)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}