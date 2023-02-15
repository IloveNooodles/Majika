package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ListRestoranBinding
import com.example.myapplication.models.Restaurant

class RestaurantAdapter(
    private val restaurantData: List<Restaurant>
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {
    
    inner class RestaurantViewHolder(private val binding: ListRestoranBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurantData: Restaurant) {
            with(binding){
                restoAddress.text = restaurantData.address
                restoPhone.text = restaurantData.phone
                restoTitle.text = restaurantData.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val adapterLayout =  ListRestoranBinding.inflate(LayoutInflater.from(parent.context) ,parent, false)
        return RestaurantViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantData[position])
        // TODO: Add map button intent
    }

    override fun getItemCount() = restaurantData.size
}
