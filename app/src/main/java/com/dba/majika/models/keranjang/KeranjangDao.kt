package com.dba.majika.models.keranjang

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KeranjangDao {
    @Query("select * from keranjang")
    fun getItems(): LiveData<List<KeranjangDatabaseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(keranjangItem: KeranjangDatabaseEntity)
    
    @Update
    suspend fun updateItem(keranjangItem: KeranjangDatabaseEntity)
    
    @Delete
    suspend fun deleteItem(keranjangItem: KeranjangDatabaseEntity)
}
