package com.dba.majika.models.keranjang

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KeranjangDao {
    @Query("select * from keranjang")
    fun getItems(): LiveData<List<KeranjangDatabaseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(keranjangItem: KeranjangDatabaseEntity)
    
    @Update
    fun updateItem(keranjangItem: KeranjangDatabaseEntity)
    
    @Delete
    fun deleteItem(keranjangItem: KeranjangDatabaseEntity)

    @Query("delete from keranjang")
    fun deleteAll()
}
