package com.dba.majika.data

import com.dba.majika.models.Restaurant

class RestoranDatasource {
    fun loadRestorans(): List<Restaurant> {
        return listOf<Restaurant>(
            Restaurant("Cabang 1", "Alamat 1", "NoTelp 1", 0),
            Restaurant("Cabang 2", "Alamat 2", "NoTelp 2", 0),
            Restaurant("Cabang 3", "Alamat 3", "NoTelp 3", 0),
            Restaurant("Cabang 4", "Alamat 4", "NoTelp 4", 0),
            Restaurant("Cabang 5", "Alamat 5", "NoTelp 5", 0),
            Restaurant("Cabang 6", "Alamat 6", "NoTelp 6", 0),
        )
    }
}
