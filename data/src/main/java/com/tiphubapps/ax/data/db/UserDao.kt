package com.tiphubapps.ax.data.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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





    /**
     * Observes list of users.
     *
     * @return all users.
     */
    @Query("SELECT * FROM Users")
    fun observeUsers(): Flow<List<UserEntity>>

    /**
     * Observes a single user.
     *
     * @param userId the user id.
     * @return the user with userId.
     */
    @Query("SELECT * FROM Users WHERE userId = :userId")
    fun observeUserById(userId: String): Flow<UserEntity>

    /**
     * Select all users from the users table.
     *
     * @return all users.
     */
    @Query("SELECT * FROM Users")
    suspend fun getUsers(): List<UserEntity>

    /**
     * Select a user by id.
     *
     * @param userId the user id.
     * @return the user with userId.
     */
    @Query("SELECT * FROM Users WHERE userId = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    /**
     * Insert a user in the database. If the user already exists, replace it.
     *
     * @param userEntity the user to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    /**
     * Update a user.
     *
     * @param userEntity user to be updated
     * @return the number of users updated. This should always be 1.
     */
    @Update
    suspend fun updateUser(userEntity: UserEntity): Int

    /**
     * Update the complete status of a user
     *
     * @param userId    id of the user
     * @param completed status to be updated
     */
    //@Query("UPDATE users SET completed = :completed WHERE userId = :userId")
    //suspend fun updateCompleted(userId: String, completed: Boolean)

    /**
     * Delete a user by id.
     *
     * @return the number of users deleted. This should always be 1.
     */
    @Query("DELETE FROM Users WHERE userId = :userId")
    suspend fun deleteUserById(userId: String): Int

    /**
     * Delete all users.
     */
    @Query("DELETE FROM Users")
    suspend fun deleteUsers()

    /**
     * Delete all completed users from the table.
     *
     * @return the number of users deleted.
     */
    //@Query("DELETE FROM Users WHERE completed = 1")
    //suspend fun deleteCompletedUsers(): Int
}