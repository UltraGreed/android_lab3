package com.example.androidlab3.gui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab3.domain.AutoEntity
import com.example.androidlab3.R

class AutoRecyclerAdapter (private val onItemClick: (id: Int) -> Unit):
    ListAdapter<AutoEntity, AutoRecyclerAdapter.AutoViewHolder>(AutoDiffUtil()) {

    private lateinit var originalList: List<AutoEntity>
    private var filterModelQuery: String? = null
    private var filterFuelQuery: String? = null

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
        val auto = currentList[position]

        holder.modelTextView.text = auto.modelName
        holder.yearTextView.text = "${auto.prodYear} год"
        holder.fuelTextView.text = auto.fuelType
        holder.odometerTextView.text = "${auto.odometerValue} 000 км"
        holder.powerTextView.text = "${auto.powerCapacity} л.с."

        val fuelColorMap = mapOf(
            "Бензин" to R.color.green,
            "Дизель" to R.color.red,
            "Электро" to R.color.brown,
            "Гибрид" to R.color.gray,
        )

        holder.cardView.setCardBackgroundColor(fuelColorMap[auto.fuelType]!!)

        holder.itemView.setOnClickListener{
            onItemClick(auto.id)
            Log.d("test", auto.id.toString())
        }
    }

    fun filterModel(query: String?, useLastQuery: Boolean = false) {
        if (!useLastQuery)
            filterModelQuery = query

        filter()
    }

    fun filterFuel(query: String?, useLastQuery: Boolean = false) {
        if (!useLastQuery)
            filterFuelQuery = query

        filter()
    }

    private fun filter(){
        var filteredList = originalList.toList()
        if (filterModelQuery != null) {
            filteredList = filteredList.filter { it.modelName.lowercase().contains(filterModelQuery!!.lowercase()) }
        }

        if (filterFuelQuery != null) {
            filteredList = filteredList.filter { it.fuelType == filterFuelQuery}
        }

        submitList(filteredList)
    }

    fun submitListNewData(list: List<AutoEntity>?) {
        super.submitList(list)

        originalList = list ?: listOf()
    }

    class AutoDiffUtil : DiffUtil.ItemCallback<AutoEntity>() {
        override fun areItemsTheSame(p0: AutoEntity, p1: AutoEntity): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: AutoEntity, p1: AutoEntity): Boolean {
            return p0.modelName == p1.modelName &&
                    p0.prodYear == p1.prodYear &&
                    p0.fuelType == p1.fuelType &&
                    p0.powerCapacity == p1.powerCapacity &&
                    p0.odometerValue == p1.odometerValue &&
                    p0.id == p1.id
        }
    }
}
