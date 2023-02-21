package com.dba.majika.ui.menu

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.dba.majika.database.getDatabase
import com.dba.majika.models.menu.*
import com.dba.majika.repository.KeranjangRepository
import com.dba.majika.repository.MenuRepository
import kotlinx.coroutines.launch
import java.io.IOException

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = MenuRepository(getDatabase(application))
    private val keranjangRepository = KeranjangRepository(getDatabase(application))
    
    val menu: MediatorLiveData<List<MenuListItem>> = MediatorLiveData();
    val filter: MutableLiveData<String> = MutableLiveData();
    
    init {
        filter.value = "";
        menu.addSource(repository.menu) { res ->
            menu.value = res.filter {
                it is MenuHeaderItem ||
                        (it is MenuItem && filter.value!!.uppercase() in it.name.uppercase())
            }
        }
        menu.addSource(filter) { res ->
            menu.value = repository.menu.value?.filter {
                it is MenuHeaderItem ||
                        (it is MenuItem && res!!.uppercase() in it.name.uppercase())
            }
        }
        refreshData()
    }
    
    fun updateItem(item: MenuItem) {
        viewModelScope.launch {
            repository.updateItems(item)
        }
    }
    
    fun refreshData() = viewModelScope.launch {
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
