package com.dba.majika.data

import com.dba.majika.models.keranjang.KeranjangItem

class KeranjangDataSource {
    companion object {
        fun loadMenu(): List<KeranjangItem> {
            return listOf<KeranjangItem>(
                KeranjangItem("Omurice", 12000, 2323),
                KeranjangItem("Omurice", 12002, 2326),
                KeranjangItem("Omurice", 12003, 2323),
                KeranjangItem("Omurice", 12000, 2324)
            )
        }
    }
    
}
