package com.farhan.tanvir.data.repository.dataSource
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun insertNewUser(user: User)
    fun insertNewUsers(userList: List<User>)
    fun getAllUsers(): Flow<PagingData<User>>
    fun getAllUsersWithReservation(): Flow<PagingData<User>>
    fun getAllUsersWithoutReservation(): Flow<PagingData<User>>
    fun deleteAllUsers()
}