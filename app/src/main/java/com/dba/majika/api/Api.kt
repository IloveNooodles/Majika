package com.dba.majika.api

import com.dba.majika.models.restaurant.RestaurantResponse
import retrofit2.Call
import retrofit2.http.GET


interface Api {
    @GET("branch")
    fun getRestaurants(): Call<RestaurantResponse>
}