package com.dba.majika.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dba.majika.databinding.ListKeranjangBinding
import com.dba.majika.models.keranjang.KeranjangItem

class KeranjangAdapter(private val list: List<KeranjangItem>) :
    RecyclerView.Adapter<KeranjangAdapter.KeranjangViewHolder>() {
    
    inner class KeranjangViewHolder(private val binding: ListKeranjangBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: KeranjangItem) {
            with(this.binding) {
                keranjangCardTitle.text = item.name
                keranjangCardPrice.text = item.price.toString()
                keranjangItemCount.text = item.total.toString()
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
    }
    
    override fun getItemCount(): Int {
        return list.size
    }
    
}
