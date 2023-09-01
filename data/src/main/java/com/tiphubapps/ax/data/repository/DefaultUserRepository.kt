package com.tiphubapps.ax.data.repository

import androidx.lifecycle.LiveData
import com.tiphubapps.ax.data.db.Converters
import com.tiphubapps.ax.domain.model.Item
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import com.tiphubapps.ax.domain.repository.ItemsRepository

import com.tiphubapps.ax.data.repository.dataSourceImpl.ItemsLocalDataSource
import com.tiphubapps.ax.data.repository.dataSourceImpl.ItemsRemoteDataSource
import kotlinx.coroutines.flow.Flow

import com.tiphubapps.ax.domain.repository.UseCaseResult
import com.tiphubapps.ax.domain.repository.UseCaseResult.UseCaseSuccess
import com.tiphubapps.ax.domain.repository.UseCaseResult.UseCaseError
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest

/**
 * Concrete implementation to load items from the data sources into a cache.
 */
class DefaultItemsRepository(
    private val itemsRemoteDataSource: ItemsRemoteDataSource,
    private val itemsLocalDataSource: ItemsLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : ItemsRepository {

    override suspend fun getItems(forceUpdate: Boolean): UseCaseResult<List<Item>> {
        if (forceUpdate) {
            try {
                updateItemsFromRemoteDataSource()
            } catch (ex: Exception) {
                return UseCaseError(ex)
            }
        }

        return when(val resp = itemsLocalDataSource.getItems()) {
            is Result.Loading -> UseCaseResult.Loading
            is Error -> UseCaseError(resp.exception)
            is Success -> {
                UseCaseSuccess(resp.data.map{
                    Converters.itemFromItemEntity(it)
                })
            }
        }
    }

    override suspend fun refreshItems() {
        updateItemsFromRemoteDataSource()
    }

    override fun observeItems(): Flow<UseCaseResult<List<Item>>> {
        return itemsLocalDataSource.observeItems().mapLatest {
            when (it) {
                is Result.Loading -> UseCaseResult.Loading
                is Error -> UseCaseError(it.exception)
                is Success -> {
                    UseCaseSuccess(it.data.map { itemEntity ->
                        Converters.itemFromItemEntity(itemEntity)
                    })
                }
            }
        }
    }

    override suspend fun refreshItem(itemId: String) {
        updateItemFromRemoteDataSource(itemId)
    }

    private suspend fun updateItemsFromRemoteDataSource() {
        val remoteItems = itemsRemoteDataSource.getItems()

        if (remoteItems is Success) {
            // Real apps might want to do a proper sync.
            itemsLocalDataSource.deleteAllItems()
            remoteItems.data.forEach { item ->
                itemsLocalDataSource.saveItem(item)
            }
        } else if (remoteItems is Result.Error) {
            throw remoteItems.exception
        }
    }

    override fun observeItem(itemId: String): Flow<UseCaseResult<Item>> {
        return itemsLocalDataSource.observeItem(itemId).mapLatest {
            when (it) {
                is Result.Loading -> UseCaseResult.Loading
                is Error -> UseCaseError(it.exception)
                is Success -> {
                    UseCaseSuccess(
                        Converters.itemFromItemEntity(it.data)
                    )
                }
            }
        }
    }

    private suspend fun updateItemFromRemoteDataSource(itemId: String) {
        val remoteItem = itemsRemoteDataSource.getItem(itemId)

        if (remoteItem is Success) {
            itemsLocalDataSource.saveItem(remoteItem.data)
        }
    }

    /**
     * Relies on [getItems] to fetch data and picks the item with the same ID.
     */
    override suspend fun getItem(itemId: String, forceUpdate: Boolean): UseCaseResult<Item> {
        if (forceUpdate) {
            updateItemFromRemoteDataSource(itemId)
        }
        //return itemsLocalDataSource.getItem(itemId)

        return when(val resp = itemsLocalDataSource.getItem(itemId)) {
            is Result.Loading -> UseCaseResult.Loading
            is Error -> UseCaseError(resp.exception)
            is Success -> {
                UseCaseSuccess(Converters.itemFromItemEntity(resp.data))
            }
        }
    }

    override suspend fun saveItem(item: Item) {
        coroutineScope {
            launch { itemsRemoteDataSource.saveItem(Converters.itemEntityFromItem(item)) }
            launch { itemsLocalDataSource.saveItem(Converters.itemEntityFromItem(item)) }
        }
    }

    override suspend fun completeItem(item: Item) {
        coroutineScope {
            launch { itemsRemoteDataSource.completeItem(Converters.itemEntityFromItem(item)) }
            launch { itemsLocalDataSource.completeItem(Converters.itemEntityFromItem(item)) }
        }
    }

    override suspend fun completeItem(itemId: String) {
        withContext(ioDispatcher) {
            (getItemWithId(itemId) as? Success)?.let { it ->
                completeItem(it.data)
            }
        }
    }

    override suspend fun activateItem(item: Item) = withContext<Unit>(ioDispatcher) {
        coroutineScope {
            launch { itemsRemoteDataSource.activateItem(Converters.itemEntityFromItem(item)) }
            launch { itemsLocalDataSource.activateItem(Converters.itemEntityFromItem(item)) }
        }
    }

    override suspend fun activateItem(itemId: String) {
        withContext(ioDispatcher) {
            (getItemWithId(itemId) as? Success)?.let { it ->
                activateItem(it.data)
            }
        }
    }

    override suspend fun clearCompletedItems() {
        coroutineScope {
            launch { itemsRemoteDataSource.clearCompletedItems() }
            launch { itemsLocalDataSource.clearCompletedItems() }
        }
    }

    override suspend fun deleteAllItems() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { itemsRemoteDataSource.deleteAllItems() }
                launch { itemsLocalDataSource.deleteAllItems() }
            }
        }
    }

    override suspend fun deleteItem(itemId: String) {
        coroutineScope {
            launch { itemsRemoteDataSource.deleteItem(itemId) }
            launch { itemsLocalDataSource.deleteItem(itemId) }
        }
    }

    private suspend fun getItemWithId(id: String): Result<Item> {

        return when(val resp = itemsLocalDataSource.getItem(id)) {
            is Result.Loading -> resp
            is Error -> resp
            is Success -> {
                Success(Converters.itemFromItemEntity(resp.data))
            }
        }
    }
}