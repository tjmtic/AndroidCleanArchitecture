package com.farhan.tanvir.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUsers(users: List<User>)

    @Query("SELECT * FROM users")
    fun getAllUsers(): PagingSource<Int, User>

    @Query("SELECT * FROM users WHERE reserved = 1")
    fun getAllUsersWithReservation(): PagingSource<Int, User>

    @Query("SELECT * FROM users WHERE reserved = 0")
    fun getAllUsersWithoutReservation(): PagingSource<Int, User>

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUser(userId: Int): Flow<User>

    @Query("DELETE FROM users")
    fun deleteAllUsers()
}