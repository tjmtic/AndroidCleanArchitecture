package com.tiphubapps.ax.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tiphubapps.ax.data.repository.dataSource.ItemEntity
import kotlinx.coroutines.flow.Flow


/**
 * Data Access Object for the items table.
 */
@Dao
interface ItemsDao {

    /**
     * Observes list of items.
     *
     * @return all items.
     */
    @Query("SELECT * FROM Items")
    fun observeItems(): Flow<List<ItemEntity>>

    /**
     * Observes a single item.
     *
     * @param itemId the item id.
     * @return the item with itemId.
     */
    @Query("SELECT * FROM Items WHERE entryid = :itemId")
    fun observeItemById(itemId: String): Flow<ItemEntity>

    /**
     * Select all items from the items table.
     *
     * @return all items.
     */
    @Query("SELECT * FROM Items")
    suspend fun getItems(): List<ItemEntity>

    /**
     * Select a item by id.
     *
     * @param itemId the item id.
     * @return the item with itemId.
     */
    @Query("SELECT * FROM Items WHERE entryid = :itemId")
    suspend fun getItemById(itemId: String): ItemEntity?

    /**
     * Insert a item in the database. If the item already exists, replace it.
     *
     * @param itemEntity the item to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(itemEntity: ItemEntity)

    /**
     * Update a item.
     *
     * @param itemEntity item to be updated
     * @return the number of items updated. This should always be 1.
     */
    @Update
    suspend fun updateItem(itemEntity: ItemEntity): Int

    /**
     * Update the complete status of a item
     *
     * @param itemId    id of the item
     * @param completed status to be updated
     */
    @Query("UPDATE items SET completed = :completed WHERE entryid = :itemId")
    suspend fun updateCompleted(itemId: String, completed: Boolean)

    /**
     * Delete a item by id.
     *
     * @return the number of items deleted. This should always be 1.
     */
    @Query("DELETE FROM Items WHERE entryid = :itemId")
    suspend fun deleteItemById(itemId: String): Int

    /**
     * Delete all items.
     */
    @Query("DELETE FROM Items")
    suspend fun deleteItems()

    /**
     * Delete all completed items from the table.
     *
     * @return the number of items deleted.
     */
    @Query("DELETE FROM Items WHERE completed = 1")
    suspend fun deleteCompletedItems(): Int
}