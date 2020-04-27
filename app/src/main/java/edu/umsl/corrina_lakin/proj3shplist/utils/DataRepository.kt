package edu.umsl.corrina_lakin.proj3shplist.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import edu.umsl.corrina_lakin.proj3shplist.data.ShpListDatabase
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpItem
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpList
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpListInfo
import java.util.concurrent.Executors

object DataRepository {

    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private val executor by lazy { Executors.newSingleThreadExecutor() }
    private lateinit var database: ShpListDatabase

    fun createDatabse(context: Context) {
        database = ShpListDatabase.getInstance(context.applicationContext)
    }

    // ShpList operations
    fun getShpLists(callback: (List<ShpList>) -> Unit) {
        executor.execute {
            val list = database.ShpListDao().getShpLists()

            // trigger callback on main ui thread
            handler.post { callback.invoke(list) }
        }
    }

    // get specific ShpList
    fun getShpListsById(id: Long, callback: (ShpListInfo) -> Unit) {
        executor.execute {
            val list = database.ShpListDao().getShpListById(id)

            // trigger callback on main ui thread
            handler.post { callback.invoke(list) }
        }
    }

    // create shplist
    fun addShpList(ShpList: ShpList, callback: (ShpList) -> Unit) {
        executor.execute {
            val id = database.ShpListDao().addShpList(ShpList)
            ShpList.id = id

            // trigger callback on main ui thread
            handler.post { callback.invoke(ShpList) }
        }
    }

    // delete ShpList
    fun deleteShpList(shpListToDelete: ShpList, callback: () -> Unit) {
        executor.execute {
            database.ShpListDao().deleteShpList(shpListToDelete)

            // trigger callback on main ui thread
            handler.post { callback.invoke() }
        }
    }

    // update ShpList
    fun updateShpList(itemToUpdate: ShpList, callback: () -> Unit) {
        executor.execute {
            database.ShpListDao().updateShpList(itemToUpdate)

            // trigger callback on main ui thread
            handler.post { callback.invoke() }
        }
    }

    // add new ShpItem
    fun addShpItem(newItem: ShpItem, callback: (ShpItem) -> Unit) {
        executor.execute {
            val id = database.ShpItemsDao().addShpItem(newItem)
            newItem.id = id

            // trigger callback on main ui thread
            handler.post { callback.invoke(newItem) }
        }
    }

    // update ShpItem
    fun updateShpItem(itemToUpdate: ShpItem, callback: () -> Unit) {
        executor.execute {
            database.ShpItemsDao().updateShpItem(itemToUpdate)

            // trigger callback on main ui thread
            handler.post { callback.invoke() }
        }
    }

    // delete ShpItem
    fun deleteShpItem(itemToDelete: ShpItem, callback: () -> Unit) {
        executor.execute {
            database.ShpItemsDao().deleteShpItem(itemToDelete)

            // trigger callback on main ui thread
            handler.post { callback.invoke() }
        }
    }
}