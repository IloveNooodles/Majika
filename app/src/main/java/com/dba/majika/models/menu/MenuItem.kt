package com.dba.majika.models.menu

import com.google.gson.annotations.SerializedName

data class MenuItem(
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("sold")
    val sold: Int,
    @SerializedName("description")
    val desc: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("quantity")
    val quantity: Int
) : MenuListItem() {
    override fun getType(): Int {
        return TYPE_MENU
    }
}

fun MenuItem.asDatabaseModel(): MenuDatabaseEntity {
    return MenuDatabaseEntity(
        name = name,
        price = price,
        sold = sold,
        desc = desc,
        type = type,
    )
}
