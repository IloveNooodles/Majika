package com.dba.majika.ui.restaurant

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dba.majika.database.getDatabase
import com.dba.majika.repository.RestaurantRepository
import kotlinx.coroutines.launch
import java.io.IOException

class RestaurantViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = RestaurantRepository(getDatabase(application))
    val restaurants = repository.restaurants

    init {
        refreshData()
    }

    fun refreshData() = viewModelScope.launch {
        try {
            repository.refreshRestaurants()
            Log.d("viewModel", "restaurant successful")
        } catch (networkError: IOException) {
            Log.d("viewModel", "restaurant failed")
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RestaurantViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}