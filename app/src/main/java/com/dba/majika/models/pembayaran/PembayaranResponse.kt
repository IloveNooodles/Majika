package com.dba.majika.models.pembayaran

import com.google.gson.annotations.SerializedName

data class PembayaranResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String
)