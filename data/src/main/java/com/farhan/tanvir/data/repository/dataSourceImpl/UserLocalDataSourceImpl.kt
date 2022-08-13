package com.farhan.tanvir.data.repository.dataSourceImpl

import com.farhan.tanvir.data.db.UserDao
import com.farhan.tanvir.data.repository.dataSource.UserLocalDataSource
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.flow.Flow


class UserLocalDataSourceImpl(private val userDao: UserDao) : UserLocalDataSource {
    override fun getUsersFromDB(userId: Int): Flow<User> = userDao.getUser(userId)
}