package com.dba.majika.ui.restaurant

import com.dba.majika.models.restaurant.Restaurant

interface RestaurantListener {
    fun onDataLoad(restaurants: List<Restaurant>)
}