package com.dba.majika.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val baseUrl =
        "https://011b-2001-448a-3040-6667-80ba-5512-259a-c8ca.ap.ngrok.io/v1/"
    
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
