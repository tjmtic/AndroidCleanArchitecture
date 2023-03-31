package com.farhan.tanvir.data.repository.dataSource
import androidx.paging.PagingSource
import com.farhan.tanvir.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserLocalEncryptedDataSource {
    fun getUserStoredCredentials(): Flow<String>
}

