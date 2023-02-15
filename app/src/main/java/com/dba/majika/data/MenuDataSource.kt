package com.dba.majika.data

import com.dba.majika.models.MenuItem
class MenuDataSource {
    fun loadMenu(): List<MenuItem> {
        return listOf<MenuItem>(
            MenuItem("Omurice", "12000", "2323", "Oishiku Naaareee"),
            MenuItem("Omurice", "12002", "2326", "Oishiku Naaareee"),
            MenuItem("Omurice", "12003", "2323", "Oishiku Naaareee"),
            MenuItem("Omurice", "12000", "2324", "Oishiku Naaareee")
        )
    }
}
