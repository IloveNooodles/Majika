package com.dba.majika.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dba.majika.api.RetrofitClient
import com.dba.majika.models.menu.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val _menu = MutableLiveData<List<MenuListItem>>()

    val menu: LiveData<List<MenuListItem>>
        get() = _menu
    init {
        // TODO: Replace with a call to the refreshDataFromRepository9) method
        getDataFromNetwork()
    }

    private fun getDataFromNetwork() = viewModelScope.launch {
        /*
        try {
            val menuRes = RetrofitClient.api.getMenu()
            _menu.postValue(menuRes.body().asDomainModel())

            Log.d("retrofit", "menu successful")

        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            Log.d("retrofit", "menu error")
        }
         */

        try {
            val menuResult = RetrofitClient.api.getMenu().enqueue(
                object : Callback<MenuListResponse> {
                    override fun onFailure(call: Call<MenuListResponse>, t: Throwable) {
                        Log.d("retrofit", "menu error")
                    }
                    override fun onResponse(call: Call<MenuListResponse>, response: Response<MenuListResponse>) {
                        Log.d("retrofit", "menu successful")
                        _menu.postValue(response.body().asDomainModel())
                    }
                }
            )
            Log.d("viewModel", "menu successful")
        } catch (networkError: IOException) {
            // Show a Toast error message and hide the progress bar.
            Log.d("viewModel", "menu failed")
        }

    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MenuViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
