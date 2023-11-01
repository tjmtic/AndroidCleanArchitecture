package com.tiphubapps.ax.data.repository.dataSourceImpl

import com.tiphubapps.ax.data.db.ItemsDao
import com.tiphubapps.ax.data.repository.dataSource.ItemEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import com.tiphubapps.ax.data.repository.dataSource.DefaultDataSource
import kotlinx.coroutines.flow.Flow


/**
 * Concrete implementation of a data source as a db.
 */
class ItemsLocalDataSource internal constructor(
    private val ItemsDao: ItemsDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DefaultDataSource {

    override fun observeItems(): Flow<Result<List<ItemEntity>>> {
        return ItemsDao.observeItems().map {
            Success(it)
        }
    }

    override fun observeItem(ItemId: String): Flow<Result<ItemEntity>> {
        return ItemsDao.observeItemById(ItemId).map {
            Success(it)
        }
    }

    override suspend fun refreshItem(ItemId: String) {
        //NO-OP
    }

    override suspend fun refreshItems() {
        //NO-OP
    }

    override suspend fun getItems(): Result<List<ItemEntity>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(ItemsDao.getItems())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getItem(ItemId: String): Result<ItemEntity> = withContext(ioDispatcher) {
        try {
            val Item = ItemsDao.getItemById(ItemId)
            if (Item != null) {
                return@withContext Success(Item)
            } else {
                return@withContext Error(Exception("Item not found!"))
            }
        } catch (e: Exception) {
            return@withContext Error(e)
        }
    }

    override suspend fun saveItem(ItemEntity: ItemEntity) = withContext(ioDispatcher) {
        ItemsDao.insertItem(ItemEntity)
    }

    override suspend fun saveItems(itemEntities: List<ItemEntity>) = withContext(ioDispatcher) {
        ItemsDao.insertItems(itemEntities)
    }

    override suspend fun updateItem(ItemEntity: ItemEntity) = withContext(ioDispatcher) {
        ItemsDao.updateItem(ItemEntity)
    }

    override suspend fun updateItems(itemEntities: List<ItemEntity>) = withContext(ioDispatcher) {
        ItemsDao.updateItems(itemEntities)
    }

    override suspend fun completeItem(ItemEntity: ItemEntity) = withContext(ioDispatcher) {
        ItemsDao.updateCompleted(ItemEntity.id, true)
    }

    override suspend fun completeItem(ItemId: String) {
        ItemsDao.updateCompleted(ItemId, true)
    }

    override suspend fun activateItem(ItemEntity: ItemEntity) = withContext(ioDispatcher) {
        ItemsDao.updateCompleted(ItemEntity.id, false)
    }

    override suspend fun activateItem(ItemId: String) {
        ItemsDao.updateCompleted(ItemId, false)
    }
    override suspend fun deleteItem(ItemEntity: String) = withContext(ioDispatcher) {
        ItemsDao.updateDeleted(ItemEntity, true)
    }
    override suspend fun deleteItems(ItemEntity: List<String>) = withContext(ioDispatcher) {
        ItemsDao.updateDeleted( ItemEntity.map{ it }, true)
    }
    override suspend fun deleteItem(ItemEntity: ItemEntity) = withContext(ioDispatcher) {
        ItemsDao.updateDeleted(ItemEntity.id, true)
    }
    override suspend fun deleteAllItems(){
        clearAllItems()
    }
    override suspend fun clearCompletedItems() = withContext<Unit>(ioDispatcher) {
        ItemsDao.deleteCompletedItems()
    }

    override suspend fun clearAllItems() = withContext(ioDispatcher) {
        ItemsDao.deleteItems()
    }

    override suspend fun clearItem(ItemId: String) = withContext<Unit>(ioDispatcher) {
        ItemsDao.deleteItemById(ItemId)
    }
}