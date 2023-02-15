package com.dba.majika.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.R
import com.dba.majika.models.MenuItem

class MenuItemAdapter (private val list:List<MenuItem>): RecyclerView.Adapter<MenuItemAdapter.Holder>(){
    inner class Holder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val menuItemName: TextView = itemView.findViewById(R.id.menu_item_name)
        private val menuItemPrice: TextView = itemView.findViewById(R.id.menu_item_price)
        private val menuItemSold: TextView = itemView.findViewById(R.id.menu_item_sold)
        private val menuItemDescription: TextView = itemView.findViewById(R.id.menu_item_description)
        private var currentItem: MenuItem? = null


        /* Bind flower name and image. */
        fun bind(item: MenuItem) {
            currentItem = item

            menuItemName.text = item.name
            menuItemPrice.text = item.price
            menuItemSold.text = item.sold
            menuItemDescription.text = item.desc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.list_menu,parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item: MenuItem = list[position]
        holder.bind(item)
    }
}
