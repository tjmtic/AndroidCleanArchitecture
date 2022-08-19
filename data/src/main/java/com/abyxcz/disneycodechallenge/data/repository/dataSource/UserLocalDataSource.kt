package com.abyxcz.disneycodechallenge.data.repository.dataSource
import androidx.paging.PagingData
import com.abyxcz.disneycodechallenge.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun insertNewUser(user: User)
    fun insertNewUsers(userList: List<User>)
    fun getAllUsers(): Flow<PagingData<User>>
    fun getAllUsersWithReservation(): Flow<PagingData<User>>
    fun getAllUsersWithoutReservation(): Flow<PagingData<User>>
    fun selectUser(user: User)
    fun unselectUser(user: User)
    fun getAllSelectedUsers(): Flow<List<User>>
    fun deleteAllUsers()
}