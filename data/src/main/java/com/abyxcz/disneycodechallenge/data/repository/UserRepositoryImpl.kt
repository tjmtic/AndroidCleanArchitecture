package com.abyxcz.disneycodechallenge.data.repository

import com.abyxcz.disneycodechallenge.data.repository.dataSource.UserLocalDataSource
import com.abyxcz.disneycodechallenge.domain.model.User
import com.abyxcz.disneycodechallenge.domain.repository.UserRepository

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

    override fun selectUser(user: User) =
        userLocalDataSource.selectUser(user)

    override fun unselectUser(user: User) =
        userLocalDataSource.unselectUser(user)

    override fun getAllSelectedUsers() =
        userLocalDataSource.getAllSelectedUsers()

    override fun deleteAllUsers() =
        userLocalDataSource.deleteAllUsers()
}