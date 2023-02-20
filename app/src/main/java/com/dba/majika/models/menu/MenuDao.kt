package com.dba.majika.models.menu

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenuDao {
    @Query("select * from menu")
    fun getMenu(): LiveData<List<MenuDatabaseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(menu: List<MenuDatabaseEntity>)
    
    @Query("delete from menu")
    fun deleteAll()
}
