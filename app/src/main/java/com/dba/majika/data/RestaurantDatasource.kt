package com.dba.majika.data

import com.dba.majika.models.Restaurant

class RestaurantDatasource {
    fun loadRestaurants(): List<Restaurant> {
        return listOf<Restaurant>(
            Restaurant("Cabang 1", "Alamat 1", "NoTelp 1", -149.5778193, 61.32927710000001),
            Restaurant("Cabang 2", "Alamat 2", "NoTelp 2", -86.2577436, 32.24569110000001),
            Restaurant("Cabang 3", "Alamat 3", "NoTelp 3", -72.3088865, 43.6692262),
            Restaurant("Cabang 4", "Alamat 4", "NoTelp 4", -85.626834, 30.221455),
            Restaurant("Cabang 5", "Alamat 5", "NoTelp 5", -81.0908408, 32.0706543),
            Restaurant("Cabang 6", "Alamat 6", "NoTelp 6", -122.038397, 37.61744100000001),
        )
    }
}
