package com.dba.majika.data

import android.util.Log
import com.dba.majika.api.RetrofitClient
import com.dba.majika.models.restaurant.Restaurant
import com.dba.majika.models.restaurant.RestaurantResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantDatasource {
    fun loadRestaurants(): List<Restaurant> {
//        val retrofitApi = RetrofitClient.api
//        retrofitApi.getRestaurants().enqueue(
//            object : Callback<RestaurantResponse> {
//                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
//                    Log.d("retrofit", "restaurant error")
//                }
//                override fun onResponse( call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {
//                    Log.d("retrofit", "restaurant successful")
//                }
//            }
//        )

        return emptyList()
    }
}
