package com.dba.majika.models.menu;

import androidx.room.Entity
import java.util.*


@Entity(tableName = "menu", primaryKeys = ["name", "price"])
data class MenuDatabaseEntity constructor(
    val name: String,
    val price: Int,
    val sold: Int,
    val desc: String,
    val type: String,
)

fun Map<MenuDatabaseEntity, Int>.asDomainModel(): List<MenuListItem> {
    val data = map { it
        MenuItem(
            name = it.key.name,
            price = it.key.price,
            sold = it.key.sold,
            desc = it.key.desc,
            type = it.key.type,
            quantity = it.value
        )
    }
    val ret = mutableListOf<MenuListItem>()
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
