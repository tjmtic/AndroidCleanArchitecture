package com.tiphubapps.ax.data.repository.dataSourceImpl

import com.tiphubapps.ax.data.db.UserDao
import com.tiphubapps.ax.data.repository.dataSource.UserLocalDataSource
import com.tiphubapps.ax.domain.model.User
import kotlinx.coroutines.flow.Flow


class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {
    override fun getUsersFromDB(userId: Int): Flow<User> = userDao.getUser(userId)
}