package com.dba.majika.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.dba.majika.models.menu.MenuDatabaseEntity
import com.dba.majika.models.restaurant.RestaurantDatabaseEntity

@Dao
interface MenuDao {
    @Query("select * from menu")
    fun getMenu(): LiveData<List<MenuDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( menu: List<MenuDatabaseEntity>)

    @Query("delete from menu")
    fun deleteAll()
}

@Dao
interface RestaurantsDao {
    @Query("select * from restaurants")
    fun getRestaurants(): LiveData<List<RestaurantDatabaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll( restaurants: List<RestaurantDatabaseEntity>)

    @Query("delete from restaurants")
    fun deleteAll()
}



@Database(entities = [MenuDatabaseEntity::class, RestaurantDatabaseEntity::class], version = 1)
abstract class MajikaDatabase: RoomDatabase() {
    abstract val menuDao: MenuDao
    abstract val restaurantsDao: RestaurantsDao
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
