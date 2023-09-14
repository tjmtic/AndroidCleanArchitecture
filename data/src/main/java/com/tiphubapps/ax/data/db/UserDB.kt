package com.tiphubapps.ax.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tiphubapps.ax.data.entity.UserEntity
import com.tiphubapps.ax.domain.model.UserRemoteKeys
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(
    entities = [UserEntity::class, UserRemoteKeys::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class, ListTypeConverter::class)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun userRemoteKeysDao(): UserRemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: UserDB? = null
        fun getInstance(context: Context, password: String): UserDB {
            val path = context.getDatabasePath("user").absolutePath
            return INSTANCE ?: synchronized(this) {
                val supportFactory = SupportFactory(SQLiteDatabase.getBytes(password.toCharArray()))
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDB::class.java,
                    path,
                )
                    .openHelperFactory(supportFactory)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}