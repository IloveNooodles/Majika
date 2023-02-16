package com.dba.majika.models.menu;
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "menu")
data class MenuDatabaseEntity constructor(
    @PrimaryKey
    val name: String,
    val price: Int,
    val sold: Int,
    val desc: String,
    val type: String
)

fun List<MenuDatabaseEntity>.asDomainModel(): List<MenuListItem> {
    val data = map {
        MenuItem(
            name = it.name,
            price = it.price,
            sold = it.sold,
            desc = it.desc,
            type = it.type
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
