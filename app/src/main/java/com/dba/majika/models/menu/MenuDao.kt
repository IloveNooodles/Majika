package com.dba.majika.models.menu

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MenuDao {
    @Query("select * from menu")
    fun getMenu(): LiveData<List<MenuDatabaseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(menu: List<MenuDatabaseEntity>)
}
