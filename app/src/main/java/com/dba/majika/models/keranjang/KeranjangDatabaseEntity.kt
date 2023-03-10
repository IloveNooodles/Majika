package com.dba.majika.models.keranjang

import androidx.room.Entity
import androidx.room.ForeignKey
import com.dba.majika.models.menu.MenuDatabaseEntity

@Entity(
    tableName = "keranjang",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = MenuDatabaseEntity::class,
            parentColumns = arrayOf("name", "price"),
            childColumns = arrayOf("name", "price"),
            onDelete = ForeignKey.CASCADE
        )
    ),
    primaryKeys = ["name", "price"]
)
data class KeranjangDatabaseEntity(
    val name: String,
    val price: Int,
    val total: Int,
)

/* transfer from db model to object model in android */
fun List<KeranjangDatabaseEntity>.asDomainModel(): List<KeranjangItem> {
    val dataKeranjang = map {
        KeranjangItem(
            name = it.name,
            price = it.price,
            total = it.total,
        )
    }
    /* add list of item to array list */
    val result = mutableListOf<KeranjangItem>()
    for (data in dataKeranjang) {
        result.add(data)
    }
    return result
}
