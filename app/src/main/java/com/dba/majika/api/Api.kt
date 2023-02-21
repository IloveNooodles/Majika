package com.dba.majika.api

import com.dba.majika.models.PaymentResponse
import com.dba.majika.models.menu.MenuListResponse
import com.dba.majika.models.restaurant.RestaurantResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface Api {
    @GET("branch")
    fun getRestaurants(): Call<RestaurantResponse>

    @GET("menu")
    fun getMenu(): Call<MenuListResponse>

    @POST("payment/{transaction_id}")
    fun postPayment(@Path("transaction_id") transaction_id: String): Call<PaymentResponse>
}