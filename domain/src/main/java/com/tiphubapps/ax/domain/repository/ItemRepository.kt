package com.tiphubapps.ax.domain.repository;

import com.tiphubapps.ax.domain.model.Item
import kotlinx.coroutines.flow.Flow


/**
 * Interface to the data layer.
 */
interface ItemsRepository {

    fun observeItems(): Flow<UseCaseResult<List<Item>>>

    suspend fun getItems(forceUpdate: Boolean = false): UseCaseResult<List<Item>>

    suspend fun refreshItems()

    fun observeItem(itemId: String): Flow<UseCaseResult<Item>>

    suspend fun getItem(itemId: String, forceUpdate: Boolean = false): UseCaseResult<Item>

    suspend fun refreshItem(itemId: String)

    suspend fun saveItem(item: Item)

    suspend fun completeItem(item: Item)

    suspend fun completeItem(itemId: String)

    suspend fun activateItem(item: Item)

    suspend fun activateItem(itemId: String)

    suspend fun clearCompletedItems()

    suspend fun deleteAllItems()

    suspend fun deleteItem(itemId: String)

    suspend fun syncData()
}


