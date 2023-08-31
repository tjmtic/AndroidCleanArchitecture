package com.tiphubapps.ax.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiphubapps.ax.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users")
    fun getAllUsers(): PagingSource<Int, UserEntity>

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUser(userId: Int): Flow<UserEntity>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}