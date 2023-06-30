package com.tiphubapps.ax.data.repository.dataSource
import androidx.paging.PagingSource
import com.tiphubapps.ax.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {
    fun getUsersFromDB(userId : Int): Flow<User>
}

