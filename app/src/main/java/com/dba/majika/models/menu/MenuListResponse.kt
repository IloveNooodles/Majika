package com.dba.majika.models.menu
import android.util.Log
import com.google.gson.annotations.SerializedName
import java.util.*

data class MenuListResponse(
    @SerializedName("data")
    val data: List<MenuItem>,
    @SerializedName("size")
    val size: Int
)

fun MenuListResponse.asDomainModel(): List<MenuListItem> {
    val ret = mutableListOf<MenuListItem>()
    Log.d("menu", data.toString())
    // adding food
    val foodHeader = MenuHeaderItem("Food")
    ret.add(foodHeader)
    for (item in data) {
        if (item.type == "Food") ret.add(item)
    }
    // adding drinks
    val drinksHeader = MenuHeaderItem("Drinks")
    ret.add(drinksHeader)
    for (item in data) {
        if (item.type == "Drink") ret.add(item)
    }
    return Collections.unmodifiableList(ret)
}

fun MenuListResponse.asDatabaseModel(): List<MenuDatabaseEntity> {
    return data.map {
        MenuDatabaseEntity(
            name = it.name,
            price = it.price,
            sold = it.sold,
            desc = it.desc,
            type = it.type
        )
    }
}