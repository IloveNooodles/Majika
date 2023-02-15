package com.dba.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.databinding.ListRestaurantBinding
import com.dba.majika.models.Restaurant

class RestaurantAdapter(
    private val restaurantData: List<Restaurant>
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    
    inner class RestaurantViewHolder(private val binding: ListRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurantData: Restaurant) {
            with(binding){
                restoAddress.text = restaurantData.address
                restoPhone.text = restaurantData.phone
                restoTitle.text = restaurantData.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val adapterLayout =  ListRestaurantBinding.inflate(LayoutInflater.from(parent.context) ,parent, false)
        return RestaurantViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val item = restaurantData[position]
        holder.bind(item)
        // TODO: Add map button intent
    }

    override fun getItemCount() = restaurantData.size
}
