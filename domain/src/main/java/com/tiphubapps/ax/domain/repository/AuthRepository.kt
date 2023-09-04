package com.tiphubapps.ax.domain.repository;

import com.tiphubapps.ax.domain.model.Item
import kotlinx.coroutines.flow.Flow


/**
 * Interface to the data layer.
 */
interface AuthRepository {

    fun getToken(): String?

    fun saveToken(token: String)

}


