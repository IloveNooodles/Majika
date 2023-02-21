package com.dba.majika.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dba.majika.database.MajikaDatabase
import com.dba.majika.models.keranjang.KeranjangDatabaseEntity
import com.dba.majika.models.keranjang.KeranjangItem
import com.dba.majika.models.keranjang.asDomainModel

class KeranjangRepository(private val database: MajikaDatabase) {
    val keranjangItems: LiveData<List<KeranjangItem>> =
        Transformations.map(database.keranjangDao.getItems()) {
            it.asDomainModel()
        }
    
    suspend fun updateItem(keranjangItem: KeranjangDatabaseEntity) {
        database.keranjangDao.updateItem(keranjangItem)
    }
    
    suspend fun insertItem(keranjangItem: KeranjangDatabaseEntity) {
        database.keranjangDao.insertItem(keranjangItem)
    }
    
    suspend fun deleteItem(keranjangItem: KeranjangDatabaseEntity) {
        database.keranjangDao.deleteItem(keranjangItem)
    }
    
}
