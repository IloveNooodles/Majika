package com.example.myapplication.data

import com.example.myapplication.model.Restoran

class RestoranDatasource {
    fun loadRestorans(): List<Restoran> {
        return listOf<Restoran>(
            Restoran("Cabang 1", "Alamat 1", "NoTelp 1", 0),
            Restoran("Cabang 2", "Alamat 2", "NoTelp 2", 0),
            Restoran("Cabang 3", "Alamat 3", "NoTelp 3", 0),
            Restoran("Cabang 4", "Alamat 4", "NoTelp 4", 0),
            Restoran("Cabang 5", "Alamat 5", "NoTelp 5", 0),
            Restoran("Cabang 6", "Alamat 6", "NoTelp 6", 0),
        )
    }
}