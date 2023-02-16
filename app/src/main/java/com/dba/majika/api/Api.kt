package com.dba.majika.api

import com.dba.majika.models.menu.MenuListResponse
import com.dba.majika.models.restaurant.RestaurantResponse
import retrofit2.Call
import retrofit2.http.GET


interface Api {
    @GET("branch")
    fun getRestaurants(): Call<RestaurantResponse>

    @GET("menu")
    fun getMenu(): Call<MenuListResponse>
}