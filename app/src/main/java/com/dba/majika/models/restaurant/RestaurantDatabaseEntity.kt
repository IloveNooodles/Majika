package com.dba.majika.models.restaurant

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "restaurants")
data class RestaurantDatabaseEntity constructor(
    @PrimaryKey
    val name: String,
    val address: String,
    val phone: String,
    val longitude: Double,
    val latitude: Double
)

fun List<RestaurantDatabaseEntity>.asDomainModel(): List<Restaurant> {
    val data = map {
        Restaurant (
            name = it.name,
            address = it.address,
            phone = it.phone,
            longitude = it.longitude,
            latitude = it.latitude
        )
    }
    val ret = mutableListOf<Restaurant>()
    for (item in data) {
        ret.add(item)
    }

    return Collections.unmodifiableList(ret)
}
