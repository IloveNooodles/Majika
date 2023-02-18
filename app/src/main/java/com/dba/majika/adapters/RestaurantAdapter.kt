package com.dba.majika.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.databinding.ListRestaurantBinding
import com.dba.majika.models.restaurant.Restaurant

class RestaurantAdapter(
    private val context: Context,
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    var list: List<Restaurant> = emptyList()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    inner class RestaurantViewHolder(private val binding: ListRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurantData: Restaurant) {
            with(binding) {
                restoAddress.text = restaurantData.address
                restoPhone.text = restaurantData.phone
                restoTitle.text = restaurantData.name
                mapsButton.tag = String.format(
                    "geo:0,0?z=15&q=%s,%s(%s)",
                    restaurantData.latitude.toBigDecimal().toPlainString(),
                    restaurantData.longitude.toBigDecimal().toPlainString(),
                    Uri.encode(restaurantData.name)
                )

                mapsButton.setOnClickListener() {
                    val gmmIntentUri =
                        Uri.parse(mapsButton.tag as String?)
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")
                    /* needs to check if the app can open intent */
                    mapIntent.resolveActivity(context.packageManager).let {
                        context.startActivity(mapIntent)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val adapterLayout =
            ListRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount() = list.size
}
