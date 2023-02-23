package com.dba.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.databinding.ListKeranjangBinding
import com.dba.majika.models.keranjang.KeranjangItem
import com.dba.majika.ui.keranjang.KeranjangViewModel

class KeranjangAdapter(viewModel: KeranjangViewModel) :
    RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder>() {
    private val viewModel = viewModel
    var list = emptyList<KeranjangItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    
    inner class KeranjangViewHolder(
        private val binding: ListKeranjangBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KeranjangItem) {
            with(this.binding) {
                keranjangCardTitle.text = item.name
                keranjangCardPrice.text = "Rp" + item.price.toString()
                    .reversed()
                    .chunked(3)
                    .joinToString(".")
                    .reversed()
                keranjangItemCount.text = item.total.toString()
            }
        }
        
        fun updateItem(item: KeranjangItem) {
            this.binding.keranjangAddItemButton.setOnClickListener {
                val newItem = KeranjangItem(item.name, item.price, item.total + 1)
                viewModel.updateItem(newItem)
            }
            this.binding.keranjangReduceItemButton.setOnClickListener {
                val tempQuantity = item.total - 1;
                if (tempQuantity <= 0) {
                    viewModel.deleteItem(item)
                } else {
                    val tempItem = KeranjangItem(item.name, item.price, item.total - 1);
                    viewModel.updateItem(tempItem)
                }
            }
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeranjangViewHolder {
        val adapterLayout =
            ListKeranjangBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return KeranjangViewHolder(adapterLayout)
    }
    
    override fun onBindViewHolder(holder: KeranjangViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.updateItem(item)
    }
    
    override fun getItemCount(): Int {
        return list.size
    }
    
}
