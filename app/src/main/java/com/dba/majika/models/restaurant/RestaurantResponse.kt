package com.dba.majika.models.restaurant

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("data")
    val data: List<Restaurant>,

    @SerializedName("size")
    val size: Int
)
