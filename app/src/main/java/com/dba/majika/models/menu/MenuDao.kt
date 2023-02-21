package com.dba.majika.models.menu

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MenuDao {
    @Query("select * from menu")
    fun getMenu(): LiveData<List<MenuDatabaseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(menu: List<MenuDatabaseEntity>)
    
    @Update
    suspend fun updateItems(menu: MenuDatabaseEntity)
    
    @Query("delete from menu")
    fun deleteAll()
}
