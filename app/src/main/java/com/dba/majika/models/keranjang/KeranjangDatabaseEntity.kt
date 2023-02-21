package com.dba.majika.models.keranjang

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keranjang")
data class KeranjangDatabaseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val price: Int,
    val total: Int,
    val itemId: Int,
)

/* transfer from db model to object model in android */
fun List<KeranjangDatabaseEntity>.asDomainModel(): List<KeranjangItem> {
    val dataKeranjang = map {
        KeranjangItem(
            name = it.name,
            price = it.price,
            total = it.total,
            itemId = it.itemId,
        )
    }
    /* add list of item to array list */
    val result = mutableListOf<KeranjangItem>()
    for (data in dataKeranjang) {
        result.add(data)
    }
    return result
}
