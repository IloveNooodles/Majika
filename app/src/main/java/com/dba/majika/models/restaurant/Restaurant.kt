package com.dba.majika.models.restaurant

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("phone_number")
    val phone: String,

    @SerializedName("longitude")
    val longitude: Double,

    @SerializedName("latitude")
    val latitude: Double
)
