package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.Restoran

class RestoranAdapter(
    private val dataset: List<Restoran>
) : RecyclerView.Adapter<RestoranAdapter.RestoranViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just an Affirmation object.
    class RestoranViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.resto_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestoranViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_restoran, parent, false)

        return RestoranViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: RestoranViewHolder, position: Int) {
        val item = dataset[position]
        holder.cardView.findViewById<TextView>(R.id.resto_title).text = item.name
        holder.cardView.findViewById<TextView>(R.id.resto_address).text = item.address
        holder.cardView.findViewById<TextView>(R.id.resto_phone).text = item.phone
        // TODO: Add map button intent
    }

    override fun getItemCount() = dataset.size
}