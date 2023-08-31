package com.tiphubapps.ax.data.repository.dataSource
import androidx.paging.PagingSource
import com.tiphubapps.ax.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getUsersFromDB(userId : Int): Flow<UserEntity?>
}

