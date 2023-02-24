package com.dba.majika.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val baseUrl =
        "http://20.5.48.98:3000/v1/"
    
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
