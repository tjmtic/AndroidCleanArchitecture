package com.abyxcz.disneycodechallenge.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abyxcz.disneycodechallenge.domain.model.User
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

    @Query("SELECT * FROM users WHERE pk = :userPk")
    fun getUser(userPk: Long): Flow<User>

    @Query("UPDATE users SET selected = 1 WHERE pk = :userPk")
    fun selectUser(userPk: Long)

    @Query("UPDATE users SET selected = 0 WHERE pk = :userPk")
    fun unselectUser(userPk: Long)

    @Query("SELECT * FROM users WHERE selected = 1")
    fun getAllSelectedUsers(): List<User>
    @Query("SELECT * FROM users WHERE selected = 1")
    fun getAllSelectedUsersFlow(): Flow<List<User>>

    @Query("DELETE FROM users")
    fun deleteAllUsers()
}