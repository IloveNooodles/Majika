package com.dba.majika.models

import com.google.gson.annotations.SerializedName

data class PaymentResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String
)