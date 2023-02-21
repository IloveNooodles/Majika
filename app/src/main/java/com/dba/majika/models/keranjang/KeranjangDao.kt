package com.dba.majika.models.keranjang

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface KeranjangDao {
    @Query("select * from keranjang")
    fun getItems(): LiveData<List<KeranjangDatabaseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(keranjangItem: KeranjangItem)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(keranjangItems: List<KeranjangDatabaseEntity>)
    
    @Update
    suspend fun addOneQuantity(id: Int)
    
    @Update
    suspend fun removeOneQuantity(id: Int)
    
    @Delete
    fun deleteItem(id: Int)
    
    @Query("delete from keranjang")
    fun deleteAll()
}
