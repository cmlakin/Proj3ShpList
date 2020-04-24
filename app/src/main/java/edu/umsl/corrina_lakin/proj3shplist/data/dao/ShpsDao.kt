package edu.umsl.corrina_lakin.proj3shplist.data.dao

import androidx.room.*
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpList
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpListInfo

@Dao
interface ShpsDao {

    @Query("SELECT * FROM shp_list")
    fun getShpLists(): List<ShpList>

    @Query("SELECT * FROM shp_list WHERE id = :id")
    fun getShpListById(id: Long): ShpListInfo

    @Insert
    fun addShpList(list: ShpList): Long

    @Update
    fun updateShpList(list: ShpList)

    @Delete
    fun deleteShpList(list: ShpList)
}

/*
    Query
    Insert
    Update
    Delete
 */