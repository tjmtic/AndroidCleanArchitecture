package com.farhan.tanvir.data.repository.dataSource

import androidx.paging.PagingData
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRemoteDataSource {
     fun getAllUsers(): Flow<PagingData<User>>
}