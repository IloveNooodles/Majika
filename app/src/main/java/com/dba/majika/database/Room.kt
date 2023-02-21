package com.dba.majika.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dba.majika.models.keranjang.KeranjangDao
import com.dba.majika.models.keranjang.KeranjangDatabaseEntity
import com.dba.majika.models.menu.MenuDao
import com.dba.majika.models.menu.MenuDatabaseEntity
import com.dba.majika.models.restaurant.RestaurantDatabaseEntity
import com.dba.majika.models.restaurant.RestaurantsDao


@Database(
    entities = [MenuDatabaseEntity::class, RestaurantDatabaseEntity::class, KeranjangDatabaseEntity::class],
    version = 1
)
abstract class MajikaDatabase : RoomDatabase() {
    abstract val menuDao: MenuDao
    abstract val restaurantsDao: RestaurantsDao
    abstract val keranjangDao: KeranjangDao
}

private lateinit var INSTANCE: MajikaDatabase

fun getDatabase(context: Context): MajikaDatabase {
    synchronized(MajikaDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                MajikaDatabase::class.java,
                "majikaDB"
            ).build()
        }
    }
    return INSTANCE
}
