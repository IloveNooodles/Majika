package com.dba.majika.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.dba.majika.api.RetrofitClient
import com.dba.majika.database.MajikaDatabase
import com.dba.majika.models.menu.MenuListItem
import com.dba.majika.models.menu.MenuListResponse
import com.dba.majika.models.menu.asDatabaseModel
import com.dba.majika.models.menu.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuRepository(private val database: MajikaDatabase) {
    val menu: LiveData<List<MenuListItem>> = Transformations.map(database.menuDao.getMenu()) {
        it.asDomainModel()
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
                                database.menuDao.deleteAll()
                                database.menuDao.insertAll((response.body().asDatabaseModel()))
                            }
                        }
                    }
                }
            }
        )
    }
}