package com.dba.majika.ui.keranjang

import android.app.Application
import androidx.lifecycle.*
import com.dba.majika.models.keranjang.KeranjangItem
import com.dba.majika.models.keranjang.asDatabaseModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class KeranjangViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = com.dba.majika.repository.KeranjangRepository
    
    val keranjangItems = repository.keranjangItems
    
    fun updateItem(keranjangItem: KeranjangItem) {
        runBlocking {
            viewModelScope.launch {
                repository.updateItem(keranjangItem.asDatabaseModel())
            }
        }
    }
    
    fun insertItem(keranjangItem: KeranjangItem) {
        runBlocking {
            viewModelScope.launch {
                repository.insertItem(keranjangItem.asDatabaseModel())
            }
        }
    }
    
    fun deleteItem(keranjangItem: KeranjangItem) {
        viewModelScope.launch {
            repository.deleteItem(keranjangItem.asDatabaseModel())
        }
    }
    
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(KeranjangViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return KeranjangViewModel(app) as T
            }
            
            throw IllegalArgumentException("Unable to construct view model")
        }
    }
}
