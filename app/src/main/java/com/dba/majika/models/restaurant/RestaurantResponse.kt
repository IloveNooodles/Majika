package com.dba.majika.models.restaurant

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("data")
    val data: List<Restaurant>,

    @SerializedName("size")
    val size: Int
)

fun RestaurantResponse.asDatabaseModel(): List<RestaurantDatabaseEntity> {
    return data.map {
        RestaurantDatabaseEntity(
            name = it.name,
            address = it.address,
            phone = it.phone,
            longitude = it.longitude,
            latitude = it.latitude
        )
    }
}