package com.abyxcz.disneycodechallenge.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abyxcz.disneycodechallenge.domain.model.User

@Database(
    entities = [User::class],
    version = 4,
    exportSchema = false
)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
}