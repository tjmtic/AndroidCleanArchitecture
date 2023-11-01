package com.tiphubapps.ax.data.repository.dataSource

import kotlinx.coroutines.flow.Flow


/**
 * Main entry point for accessing tasks data.
 */
interface DefaultDataSource {


    fun observeItems(): Flow<Result<List<ItemEntity>>>

    suspend fun getItems(): Result<List<ItemEntity>>

    suspend fun refreshItems()

    fun observeItem(taskId: String): Flow<Result<ItemEntity>>

    suspend fun getItem(taskId: String): Result<ItemEntity>

    suspend fun refreshItem(taskId: String)

    suspend fun saveItem(task: ItemEntity)

    suspend fun saveItems(task: List<ItemEntity>)

    suspend fun updateItem(task: ItemEntity) : Int

    suspend fun updateItems(task: List<ItemEntity>) : Int

    suspend fun completeItem(task: ItemEntity)

    suspend fun completeItem(taskId: String)

    suspend fun deleteItem(task: ItemEntity)

    suspend fun deleteItem(task: String)

    suspend fun deleteItems(task: List<String>)

    suspend fun deleteAllItems()

    suspend fun activateItem(task: ItemEntity)

    suspend fun activateItem(taskId: String)

    suspend fun clearCompletedItems()

    suspend fun clearAllItems()

    suspend fun clearItem(taskId: String)
}
