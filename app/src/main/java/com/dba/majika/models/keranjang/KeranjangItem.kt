package com.dba.majika.models.keranjang

import com.google.gson.annotations.SerializedName

data class KeranjangItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("total")
    val total: Int,
)

fun KeranjangItem.asDatabaseModel(): KeranjangDatabaseEntity {
    return KeranjangDatabaseEntity(name, price, total)
}
