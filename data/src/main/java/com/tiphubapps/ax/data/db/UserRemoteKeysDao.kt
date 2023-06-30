package com.tiphubapps.ax.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tiphubapps.ax.domain.model.UserRemoteKeys

@Dao
interface UserRemoteKeysDao {

    @Query("SELECT * FROM user_remote_keys WHERE id = :userId")
    suspend fun getUserRemoteKeys(userId: Int): UserRemoteKeys?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllUserRemoteKeys(userRemoteKeys : List<UserRemoteKeys>)

    @Query("DELETE FROM user_remote_keys")
    suspend fun deleteAllUserRemoteKeys()
}