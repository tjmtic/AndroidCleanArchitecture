package com.abyxcz.disneycodechallenge.data.repository.dataSourceImpl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abyxcz.disneycodechallenge.data.db.UserDao
import com.abyxcz.disneycodechallenge.data.repository.dataSource.UserLocalDataSource
import com.abyxcz.disneycodechallenge.domain.model.User
import kotlinx.coroutines.flow.Flow


class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {
    override fun insertNewUser(user: User) {
        userDao.addUser(user)
    }

    override fun insertNewUsers(userList: List<User>) {
        userDao.addUsers(userList)
    }

    override  fun getAllUsers() : Flow<PagingData<User>> {
        val pagingSourceFactory = { userDao.getAllUsers() }
        return Pager(
            config = PagingConfig(pageSize = 60),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override  fun getAllUsersWithReservation() : Flow<PagingData<User>> {
        val pagingSourceFactory = { userDao.getAllUsersWithReservation() }
        return Pager(
            config = PagingConfig(pageSize = 60),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override  fun getAllUsersWithoutReservation() : Flow<PagingData<User>> {
        val pagingSourceFactory = { userDao.getAllUsersWithoutReservation() }
        return Pager(
            config = PagingConfig(pageSize = 60),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override fun selectUser(user: User) {
        userDao.selectUser(user.pk)
    }

    override fun unselectUser(user: User) {
        userDao.unselectUser(user.pk)
    }

    override  fun getAllSelectedUsers() : Flow<List<User>> {
       return userDao.getAllSelectedUsersFlow()
    }

    override fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}