package com.dba.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.databinding.ListMenuBinding
import com.dba.majika.databinding.ListMenuHeaderBinding
import com.dba.majika.models.menu.MenuHeaderItem
import com.dba.majika.models.menu.MenuItem
import com.dba.majika.models.menu.MenuListItem

class MenuItemAdapter (): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var list: List<MenuListItem> = emptyList()
    set(value){
        field = value
        notifyDataSetChanged()
    }
    inner class MenuItemViewHolder(private val binding: ListMenuBinding) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: MenuItem? = null
        /* Bind flower name and image. */
        fun bind(item: MenuItem) {
            with(this.binding) {
                currentItem = item
    
                menuItemName.text = item.name
                menuItemPrice.text = item.price.toString()
                menuItemSold.text = item.sold.toString()
                menuItemDescription.text = item.desc
            }
        }
    }

    inner class MenuHeaderViewHolder(private val binding: ListMenuHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: MenuHeaderItem? = null
        /* Bind flower name and image. */
        fun bind(item: MenuHeaderItem) {
            with(this.binding) {
                currentItem = item

                menuItemTitleId.text = item.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 0){
            var listMenuHeaderBinding = ListMenuHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MenuHeaderViewHolder(listMenuHeaderBinding)
        }else{
            var listMenuBinding = ListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MenuItemViewHolder(listMenuBinding)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        if (item is MenuItem) return 1
        else if (item is MenuHeaderItem) return 0
        return -1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item: MenuListItem = list[position]
        if (holder is MenuHeaderViewHolder && item is MenuHeaderItem){
            holder.bind(item);
        } else if (holder is MenuItemViewHolder && item is MenuItem){
            holder.bind(item);
        }
    }
}
