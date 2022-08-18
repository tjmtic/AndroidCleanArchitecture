package com.farhan.tanvir.data.repository.dataSourceImpl

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.farhan.tanvir.data.db.UserDao
import com.farhan.tanvir.data.repository.dataSource.UserLocalDataSource
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow


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
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override  fun getAllUsersWithReservation() : Flow<PagingData<User>> {
        val pagingSourceFactory = { userDao.getAllUsersWithReservation() }
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override  fun getAllUsersWithoutReservation() : Flow<PagingData<User>> {
        val pagingSourceFactory = { userDao.getAllUsersWithoutReservation() }
        return Pager(
            config = PagingConfig(pageSize = 20),
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
         /*return flow {
             while(true) {
                 val users = userDao.getAllSelectedUsers()
                 emit(users)
             }
                    }*/

       return userDao.getAllSelectedUsersFlow()
    }

    override fun deleteAllUsers() {
        userDao.deleteAllUsers()
    }
}