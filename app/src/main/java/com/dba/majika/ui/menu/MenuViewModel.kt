package com.dba.majika.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dba.majika.database.getDatabase
import com.dba.majika.models.menu.*
import com.dba.majika.repository.MenuRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MenuViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MenuRepository(getDatabase(application))

    val menu = repository.menu
    init {
        refreshData()
    }

    private fun refreshData() = viewModelScope.launch {
        try {
            repository.refreshMenu()
            Log.d("viewModel", "menu successful")
        } catch (networkError: IOException) {
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
