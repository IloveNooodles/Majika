package com.dba.majika.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dba.majika.api.RetrofitClient
import com.dba.majika.database.MajikaDatabase
import com.dba.majika.models.restaurant.Restaurant
import com.dba.majika.models.restaurant.RestaurantResponse
import com.dba.majika.models.restaurant.asDatabaseModel
import com.dba.majika.models.restaurant.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RestaurantRepository(private val database: MajikaDatabase) {
    val restaurants: LiveData<List<Restaurant>> =
        Transformations.map(database.restaurantsDao.getRestaurants()) {
            it.asDomainModel()
        }

    suspend fun refreshRestaurants() {
        RetrofitClient.api.getRestaurants().enqueue(
            object : Callback<RestaurantResponse> {

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    Log.d("retrofit", "restaurant error")
                }

                override fun onResponse(
                    call: Call<RestaurantResponse>,
                    response: Response<RestaurantResponse>
                ) {
                    Log.d("retrofit", "restaurant successful")
                    runBlocking {
                        launch {
                            withContext(Dispatchers.IO) {
                                database.restaurantsDao.deleteAll()
                                response.body()?.asDatabaseModel().let {
                                    if (it != null) {
                                        database.restaurantsDao.insertAll(it)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}