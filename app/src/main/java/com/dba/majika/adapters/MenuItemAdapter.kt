package com.dba.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.databinding.ListMenuBinding
import com.dba.majika.databinding.ListMenuHeaderBinding
import com.dba.majika.models.keranjang.KeranjangItem
import com.dba.majika.models.menu.MenuHeaderItem
import com.dba.majika.models.menu.MenuItem
import com.dba.majika.models.menu.MenuListItem
import com.dba.majika.ui.keranjang.KeranjangViewModel
import com.dba.majika.ui.menu.MenuViewModel

class MenuItemAdapter(viewModel: MenuViewModel, keranjangViewModel: KeranjangViewModel) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: List<MenuListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    var keranjangItemList: List<KeranjangItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
    val viewModel = viewModel
    val keranjangViewModel = keranjangViewModel
    
    inner class MenuItemViewHolder(private val binding: ListMenuBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        private var currentItem: MenuItem? = null
        
        /* Bind flower name and image. */
        fun bind(item: MenuItem) {
            with(this.binding) {
                currentItem = item
                
                menuItemName.text = item.name
                menuItemPrice.text = "Rp." + item.price.toString()
                menuItemSold.text = "Sold: " + item.sold.toString()
                menuItemDescription.text = item.desc
                menuCount.text = item.quantity.toString()
            }
        }
        
        fun updateItem(keranjangItem: MenuItem) {
            /* check if items is available */
            var isAlreadyExist = false
            for (item in keranjangItemList) {
                if (keranjangItem.name == item.name) {
                    isAlreadyExist = true
                    break
                }
            }
            var qty = keranjangItem.quantity
            
            with(this.binding) {
                addItemButton.setOnClickListener {
                    val tempItem = KeranjangItem(
                        keranjangItem.name,
                        keranjangItem.price,
                        keranjangItem.quantity + 1
                    )
                    
                    val tempMenuItem = MenuItem(
                        keranjangItem.name,
                        keranjangItem.price,
                        keranjangItem.sold,
                        keranjangItem.desc,
                        keranjangItem.type,
                        keranjangItem.quantity + 1
                    )
                    if (isAlreadyExist) {
                        viewModel.updateItem(tempMenuItem)
                        keranjangViewModel.updateItem(tempItem)
                        qty += 1
                    } else {
                        /* insert the new object one */
                        keranjangViewModel.insertItem(tempItem)
                        qty = 1
                    }
                }
                
                reduceItemButton.setOnClickListener {
                    val tempQuantity = keranjangItem.quantity - 1
                    /* remove items if already 0 */
                    if (tempQuantity <= 0) {
                        val tempItem = KeranjangItem(
                            keranjangItem.name,
                            keranjangItem.price,
                            keranjangItem.quantity
                        )
                        qty = 0
                        keranjangViewModel.deleteItem(tempItem)
                    } else {
                        val tempItem = KeranjangItem(
                            keranjangItem.name,
                            keranjangItem.price,
                            keranjangItem.quantity - 1
                        )
                        val tempMenuItem = MenuItem(
                            keranjangItem.name,
                            keranjangItem.price,
                            keranjangItem.sold,
                            keranjangItem.desc,
                            keranjangItem.type,
                            keranjangItem.quantity - 1
                        )
                        qty -= 1
                        viewModel.updateItem(tempMenuItem)
                        keranjangViewModel.updateItem(tempItem)
                    }
                }
                menuCount.text = qty.toString()
            }
        }
    }
    
    inner class MenuHeaderViewHolder(private val binding: ListMenuHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
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
        if (viewType == 0) {
            var listMenuHeaderBinding =
                ListMenuHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MenuHeaderViewHolder(listMenuHeaderBinding)
        } else {
            var listMenuBinding =
                ListMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        if (holder is MenuHeaderViewHolder && item is MenuHeaderItem) {
            holder.bind(item);
        } else if (holder is MenuItemViewHolder && item is MenuItem) {
            holder.bind(item);
            holder.updateItem(item)
        }
    }
}
