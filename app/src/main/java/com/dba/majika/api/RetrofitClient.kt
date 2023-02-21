package com.dba.majika.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val baseUrl =
        "https://21b5-2001-448a-304f-6fa7-c1e9-eb9f-85ac-cdb3.ap.ngrok.io/v1/"
    
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}
