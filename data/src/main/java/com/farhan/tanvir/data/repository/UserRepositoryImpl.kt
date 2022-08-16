package com.farhan.tanvir.data.repository

import com.farhan.tanvir.data.repository.dataSource.UserLocalDataSource
import com.farhan.tanvir.domain.model.User
import com.farhan.tanvir.domain.repository.UserRepository

class UserRepositoryImpl(
    private val userLocalDataSource: UserLocalDataSource,
) :
    UserRepository {

    override fun insertNewUser(user: User) =
        userLocalDataSource.insertNewUser(user)

    override fun insertNewUsers(userList: List<User>) =
        userLocalDataSource.insertNewUsers(userList)

    override fun getAllUsers() =
        userLocalDataSource.getAllUsers()

    override fun getAllUsersWithReservation() =
        userLocalDataSource.getAllUsersWithReservation()

    override fun getAllUsersWithoutReservation() =
        userLocalDataSource.getAllUsersWithoutReservation()

    override fun deleteAllUsers() =
        userLocalDataSource.deleteAllUsers()
}