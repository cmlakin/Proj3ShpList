package edu.umsl.corrina_lakin.proj3shplist.data.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*


@Entity(
    tableName = "shp_item",
    foreignKeys = [
        ForeignKey(entity = ShpList::class, parentColumns = ["id"], childColumns = ["shpListId"], onDelete = ForeignKey.CASCADE)
    ])

@Parcelize
data class ShpItem(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    @ColumnInfo(index = true)
    var shpListId : Long,
    var itemName: String,
    var itemQuantity: Long,
    var itemPrice: Double,
    var isCompleted: Boolean,
    var createdAt: Long
) : Parcelable

{
    @Ignore
    val dateAsString: Date

    init {
        dateAsString = Date(createdAt)
    }
}