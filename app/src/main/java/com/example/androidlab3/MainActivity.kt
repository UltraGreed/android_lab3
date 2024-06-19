package com.example.androidlab3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.SearchView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val modelFuelMap = mapOf(
        R.string.toyota_crown to listOf(R.string.petrol, R.string.diesel),
        R.string.toyota_cruiser to listOf(R.string.petrol, R.string.diesel),
        R.string.toyota_mark2 to listOf(R.string.petrol, R.string.diesel),
        R.string.toyota_prius to listOf(R.string.hybrid),
        R.string.toyota_bz4x to listOf(R.string.electric)
    )
    private val modelYearMap = mapOf(
        R.string.toyota_crown to 1988..2024,
        R.string.toyota_cruiser to 1997..2008,
        R.string.toyota_mark2 to 1988..2005,
        R.string.toyota_prius to 1997..2024,
        R.string.toyota_bz4x to 2022..2024
    )
    private val fuelColorMap = mapOf(
        R.string.petrol to R.color.green,
        R.string.diesel to R.color.red,
        R.string.electric to R.color.brown,
        R.string.hybrid to R.color.gray,
    )
    private val modelPowerMap = mapOf(
        R.string.toyota_crown to 100..250,
        R.string.toyota_cruiser to 200..450,
        R.string.toyota_mark2 to 140..230,
        R.string.toyota_prius to 76..100,
        R.string.toyota_bz4x to 200..220
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AutoRecyclerAdapter(generateAutoList(20))

        val searchView: SearchView = findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                (recyclerView.adapter as AutoRecyclerAdapter).filter(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                (recyclerView.adapter as AutoRecyclerAdapter).filter(query)
                return false
            }
        })

    }

    private fun generateAutoList(
        n: Int
    ): List<AutoItem> {
        val newList = mutableListOf<AutoItem>()

        for (i in 0..<n) {
            val model = modelFuelMap.keys.random()
            val year = modelYearMap[model]!!.random()
            val fuel = modelFuelMap[model]!!.random()
            val odometer = (50..150).random() + (2024 - year) * 600 / (2024 - 1988)
            val power = modelPowerMap[model]!!.random()

            val modelText = getText(model).toString()
            val yearText = "$year год"
            val fuelText = getText(fuel).toString()
            val odometerText = "$odometer 000 км"
            val powerText = "$power л.с."
            val color = getColor(fuelColorMap[fuel]!!)

            newList.add(AutoItem(modelText, yearText, fuelText, odometerText, powerText, color))
        }

        return newList
    }
}

class AutoItem(
    val model: String,
    val year: String,
    val fuel: String,
    val odometer: String,
    val power: String,
    val color: Int
)
