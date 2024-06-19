package com.example.androidlab3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class AutoRecyclerAdapter(private var autos: List<AutoItem>) :
    RecyclerView.Adapter<AutoRecyclerAdapter.AutoViewHolder>() {
    private val autosCopy = autos.toList()

    class AutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val modelTextView: TextView = itemView.findViewById(R.id.textModel)
        val yearTextView: TextView = itemView.findViewById(R.id.textYear)
        val fuelTextView: TextView = itemView.findViewById(R.id.textFuel)
        val odometerTextView: TextView = itemView.findViewById(R.id.textOdometer)
        val powerTextView: TextView = itemView.findViewById(R.id.textPower)

        val cardView: CardView = itemView.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.autos_item, parent, false)

        return AutoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AutoViewHolder, position: Int) {
        val auto = autos[position]

        holder.modelTextView.text = auto.model
        holder.yearTextView.text = auto.year
        holder.fuelTextView.text = auto.fuel
        holder.odometerTextView.text = auto.odometer
        holder.powerTextView.text = auto.power

        holder.cardView.setCardBackgroundColor(auto.color)
    }

    override fun getItemCount(): Int {
        return autos.size
    }

    fun filter(query: String?) {
        if (query == null) {
            autos = autosCopy.toList()
        } else {
            val queryLower = query.lowercase()
            autos = autosCopy.filter {queryLower in it.model.lowercase()}
        }
        notifyDataSetChanged()
    }
}
