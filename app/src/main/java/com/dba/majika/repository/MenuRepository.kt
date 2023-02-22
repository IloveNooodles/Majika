package com.dba.majika.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import com.dba.majika.api.RetrofitClient
import com.dba.majika.database.MajikaDatabase
import com.dba.majika.models.menu.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuRepository(private val database: MajikaDatabase, private val keranjang: KeranjangRepository) {
    private val menuData: LiveData<List<MenuDatabaseEntity>> = Transformations.map(database.menuDao.getMenu()){it}
    val menu: MediatorLiveData<List<MenuListItem>> = MediatorLiveData();

    init{
        menu.addSource(menuData){
            if (keranjang.keranjangItems.value == null) return@addSource
            var data = mutableMapOf<MenuDatabaseEntity, Int>();
            for (c in it){
                data[c] = 0
            }
            keranjang.keranjangItems.value!!.map { c ->{
                    for (k in data.keys){
                        if (k.name==c.name) data[k] = c.total
                    }
                }
            }
            Log.d("Menu update", data.size.toString())
            menu.value = data.asDomainModel()
        }
        menu.addSource(keranjang.keranjangItems){
            if (menuData.value == null) return@addSource
            var data = mutableMapOf<MenuDatabaseEntity, Int>();
            for (c in menuData.value!!) data[c]=0
            for(c in it){
                for (k in data.keys){
                    if (k.name==c.name) data[k] = c.total
                }
            }
            Log.d("Keranjang update", data.size.toString())
            menu.value = data.asDomainModel()
        }
    }
    
    suspend fun refreshMenu() {
        RetrofitClient.api.getMenu().enqueue(
            object : Callback<MenuListResponse> {
                
                override fun onFailure(call: Call<MenuListResponse>, t: Throwable) {
                    Log.d("retrofit", "menu error")
                }
                
                override fun onResponse(
                    call: Call<MenuListResponse>,
                    response: Response<MenuListResponse>
                ) {
                    Log.d("retrofit", "menu successful")
                    runBlocking {
                        launch {
                            withContext(Dispatchers.IO) {
                                response.body()?.asDatabaseModel().let {
                                    if (it != null) {
                                        database.menuDao.insertAll(it)
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
