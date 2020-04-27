package edu.umsl.corrina_lakin.proj3shplist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.umsl.corrina_lakin.proj3shplist.data.dao.ShpsDao
import edu.umsl.corrina_lakin.proj3shplist.data.dao.ShpsItemDao
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpItem
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpList

@Database(
    entities = [ShpList::class, ShpItem::class],
    version = 2,
    exportSchema = false
)
abstract class ShpListDatabase: RoomDatabase() {

    abstract fun ShpListDao(): ShpsDao
    abstract fun ShpItemsDao(): ShpsItemDao


    companion object {
        // name of the db file
        private const val DATABASE_FILE = "ShpList.db"
        // reference to single instance of the db
        private lateinit var instance: ShpListDatabase


        fun getInstance(context: Context): ShpListDatabase {
            // try creating databse if not initialsed
            if (!::instance.isInitialized) {
                // prevent multi threads access
                synchronized(this) {
                    instance = Room.databaseBuilder(context, ShpListDatabase::class.java, DATABASE_FILE)
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            // return database instance
            return instance
        }
    }
}