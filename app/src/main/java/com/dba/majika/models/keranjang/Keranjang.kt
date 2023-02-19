package com.dba.majika.models.keranjang

import com.google.gson.annotations.SerializedName

data class Keranjang(
    @SerializedName("list")
    val list: List<KeranjangItem>,
    @SerializedName("totalPrice")
    val totalPrice: Int,
)
