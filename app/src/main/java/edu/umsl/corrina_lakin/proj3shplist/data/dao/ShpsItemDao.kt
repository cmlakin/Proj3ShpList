package edu.umsl.corrina_lakin.proj3shplist.data.dao

import androidx.room.*
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpItem

@Dao
interface ShpsItemDao {

    @Query("SELECT * FROM shp_item WHERE id = :id")
    fun getShpItemById(id: Long): ShpItem

    @Query("SELECT * FROM shp_item WHERE ShpListId = :listId AND isCompleted")
    fun getCompletedShpItemsByListId(listId: Long): ShpItem

    @Query("SELECT * FROM shp_item WHERE ShpListId = :listId AND NOT isCompleted")
    fun getPendingShpItemsByListId(listId: Long): ShpItem

    @Insert
    fun addShpItem(item: ShpItem): Long

    @Update
    fun updateShpItem(item: ShpItem)

    @Delete
    fun deleteShpItem(item: ShpItem)

}