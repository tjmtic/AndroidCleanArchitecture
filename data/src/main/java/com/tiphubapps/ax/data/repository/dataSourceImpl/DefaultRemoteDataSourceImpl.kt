package com.tiphubapps.ax.data.repository.dataSourceImpl

import android.annotation.SuppressLint
import com.tiphubapps.ax.data.repository.dataSource.DefaultDataSource
import com.tiphubapps.ax.data.repository.dataSource.ItemEntity
import kotlinx.coroutines.delay
import com.tiphubapps.ax.data.repository.dataSource.Result
import com.tiphubapps.ax.data.repository.dataSource.Result.Success
import com.tiphubapps.ax.data.repository.dataSource.Result.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map


/**
 * Implementation of the data source that adds a latency simulating network.
 */
object ItemsRemoteDataSource : DefaultDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, ItemEntity>(2)

    init {
        addItem("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addItem("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    private val observableItems = MutableStateFlow<Result<List<ItemEntity>>>(Result.Loading)//MutableLiveData<Result<List<Item>>>()

    @SuppressLint("NullSafeMutableLiveData")
    override suspend fun refreshItems() {
        observableItems.value = getItems()
    }

    override suspend fun refreshItem(itemId: String) {
        refreshItems()
    }

    override fun observeItems(): Flow<Result<List<ItemEntity>>> {
        return observableItems
    }

    override fun observeItem(itemId: String): Flow<Result<ItemEntity>> {
        return observableItems.map { items ->
            when (items) {
                is Result.Loading -> Result.Loading
                is Error -> Error(items.exception)
                is Success -> {
                    val item = items.data.firstOrNull() { it.id == itemId }
                        ?: return@map Error(Exception("Not found"))
                    Success(item)
                }
            }
        }
    }

    override suspend fun getItems(): Result<List<ItemEntity>> {
        // Simulate network by delaying the execution.
        val items = TASKS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Success(items)
    }

    override suspend fun getItem(itemId: String): Result<ItemEntity> {
        // Simulate network by delaying the execution.
        delay(SERVICE_LATENCY_IN_MILLIS)
        TASKS_SERVICE_DATA[itemId]?.let {
            return Success(it)
        }
        return Error(Exception("Item not found"))
    }

    private fun addItem(title: String, description: String) {
        val newItemEntity = ItemEntity(title, description)
        TASKS_SERVICE_DATA[newItemEntity.id] = newItemEntity
    }

    override suspend fun saveItem(itemEntity: ItemEntity) {
        TASKS_SERVICE_DATA[itemEntity.id] = itemEntity
    }

    override suspend fun saveItems(task: List<ItemEntity>) {
        TODO("Not yet implemented")
    }

    override suspend fun updateItem(task: ItemEntity): Int {
        TODO("Not yet implemented")
    }

    override suspend fun updateItems(task: List<ItemEntity>): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteItem(itemEntity: ItemEntity) {
        val deletedItemEntity = ItemEntity(itemEntity.title, itemEntity.description, itemEntity.isCompleted, true, itemEntity.id)
        TASKS_SERVICE_DATA[itemEntity.id] = deletedItemEntity
    }

    override suspend fun deleteItem(itemId: String) {
        // Not required for the remote data source
    }

    override suspend fun deleteItems(task: List<String>) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllItems() {
        TODO("Not yet implemented")
    }

    override suspend fun completeItem(itemEntity: ItemEntity) {
        val completedItemEntity = ItemEntity(itemEntity.title, itemEntity.description, true, itemEntity.isDeleted, itemEntity.id)
        TASKS_SERVICE_DATA[itemEntity.id] = completedItemEntity
    }

    override suspend fun completeItem(itemId: String) {
        // Not required for the remote data source
    }

    override suspend fun activateItem(itemEntity: ItemEntity) {
        val activeItemEntity = ItemEntity(itemEntity.title, itemEntity.description, false, itemEntity.isDeleted, itemEntity.id)
        TASKS_SERVICE_DATA[itemEntity.id] = activeItemEntity
    }

    override suspend fun activateItem(itemId: String) {
        // Not required for the remote data source
    }

    override suspend fun clearCompletedItems() {
        TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, ItemEntity>
    }

    override suspend fun clearAllItems() {
        TASKS_SERVICE_DATA.clear()
    }

    override suspend fun clearItem(itemId: String) {
        TASKS_SERVICE_DATA.remove(itemId)
    }
}