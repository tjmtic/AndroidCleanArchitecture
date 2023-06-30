package com.tiphubapps.ax.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiphubapps.ax.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(users: List<User>)

    @Query("SELECT * FROM users")
    fun getAllUsers(): PagingSource<Int, User>

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUser(userId: Int): Flow<User>

    @Query("DELETE FROM users")
    suspend fun deleteAllUsers()
}