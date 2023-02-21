package com.dba.majika.models.restaurant

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantsDao {
    @Query("select * from restaurants order by name")
    fun getRestaurants(): LiveData<List<RestaurantDatabaseEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(restaurants: List<RestaurantDatabaseEntity>)
    
    @Query("delete from restaurants")
    fun deleteAll()
}
