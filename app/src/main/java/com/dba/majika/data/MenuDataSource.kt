package com.dba.majika.data

import com.dba.majika.models.menu.MenuHeaderItem
import com.dba.majika.models.menu.MenuItem
import com.dba.majika.models.menu.MenuListItem

class MenuDataSource {
    fun loadMenu(): List<MenuListItem> {
        return listOf<MenuListItem>(
            MenuHeaderItem("Food"),
            MenuItem("Omurice", 12000, 2323, "Oishiku Naaareee", "Food"),
            MenuItem("Omurice", 12002, 2326, "Oishiku Naaareee", "Food"),
            MenuHeaderItem("Drinks"),
            MenuItem("Omurice", 12003, 2323, "Oishiku Naaareee", "Drink"),
            MenuItem("Omurice", 12000, 2324, "Oishiku Naaareee", "Drink")
        )
    }
}
