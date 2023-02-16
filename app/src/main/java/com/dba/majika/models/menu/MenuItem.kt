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
    val type: String) : MenuListItem() {
    override fun getType(): Int {
        return TYPE_MENU
    }
}
