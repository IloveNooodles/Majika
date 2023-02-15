package com.dba.majika.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.R
import com.dba.majika.databinding.ListMenuBinding
import com.dba.majika.models.MenuItem

class MenuItemAdapter (private val list:List<MenuItem>): RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>(){
    inner class MenuItemViewHolder(private val binding: ListMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: MenuItem? = null
        /* Bind flower name and image. */
        fun bind(item: MenuItem) {
            with(this.binding) {
                currentItem = item
    
                menuItemName.text = item.name
                menuItemPrice.text = item.price
                menuItemSold.text = item.sold
                menuItemDescription.text = item.desc
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        var listMenuBinding = ListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(listMenuBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val item: MenuItem = list[position]
        holder.bind(item)
    }
}
