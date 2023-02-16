package com.dba.majika.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dba.majika.models.menu.MenuDatabaseEntity

@Dao
interface MenuDao {
    @Query("select * from menu")
    fun getMenu(): LiveData<List<MenuDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( menu: List<MenuDatabaseEntity>)
}



@Database(entities = [MenuDatabaseEntity::class], version = 1)
abstract class MajikaDatabase: RoomDatabase() {
    abstract val menuDao: MenuDao
}

private lateinit var INSTANCE: MajikaDatabase

fun getDatabase(context: Context): MajikaDatabase {
    synchronized(MajikaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MajikaDatabase::class.java,
                "majikaDB").build()
        }
    }
    return INSTANCE
}
