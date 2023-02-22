package com.dba.majika.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dba.majika.database.getDatabase
import com.dba.majika.models.keranjang.KeranjangDatabaseEntity
import com.dba.majika.models.keranjang.KeranjangItem
import com.dba.majika.models.keranjang.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

object KeranjangRepository {
    val database = getDatabase()
    val keranjangItems: LiveData<List<KeranjangItem>> =
        Transformations.map(database.keranjangDao.getItems()) {
            it.asDomainModel()
        }
    
    fun updateItem(keranjangItem: KeranjangDatabaseEntity) {
        runBlocking {
            launch{
                withContext(Dispatchers.IO){
                    database.keranjangDao.updateItem(keranjangItem)
                }
            }
        }
    }
    
    fun insertItem(keranjangItem: KeranjangDatabaseEntity) {
        runBlocking {
            launch{
                withContext(Dispatchers.IO){
                    database.keranjangDao.insertItem(keranjangItem)
                }
            }
        }
    }
    
    fun deleteItem(keranjangItem: KeranjangDatabaseEntity) {
        runBlocking {
            launch{
                withContext(Dispatchers.IO){
                    database.keranjangDao.deleteItem(keranjangItem)
                }
            }
        }
    }

    fun deleteAll() {
        runBlocking {
            launch{
                withContext(Dispatchers.IO){
                    database.keranjangDao.deleteAll()
                }
            }
        }
    }
    
}
