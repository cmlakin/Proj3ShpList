package edu.umsl.corrina_lakin.proj3shplist.data.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "shp_list")
data class ShpList(
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0,
    var name: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShpList> {
        override fun createFromParcel(parcel: Parcel): ShpList {
            return ShpList(parcel)
        }

        override fun newArray(size: Int): Array<ShpList?> {
            return arrayOfNulls(size)
        }
    }

}

data class ShpListInfo(
    @Embedded
    var shpList: ShpList,
    @Relation(parentColumn = "id", entityColumn = "shpListId", entity = ShpItem::class)
    var items : List<ShpItem>
)