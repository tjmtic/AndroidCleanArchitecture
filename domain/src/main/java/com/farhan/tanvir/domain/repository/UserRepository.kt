package com.farhan.tanvir.domain.repository

import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import org.json.JSONObject


interface UserRepository {
    fun insertNewUser(user: User)
    fun insertNewUsers(userList: List<User>)
    fun getAllUsers(): Flow<PagingData<User>>
    fun getAllUsersWithReservation(): Flow<PagingData<User>>
    fun getAllUsersWithoutReservation(): Flow<PagingData<User>>
    fun deleteAllUsers()
}