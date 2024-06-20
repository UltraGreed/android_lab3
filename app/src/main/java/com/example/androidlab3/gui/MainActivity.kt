package com.example.androidlab3.gui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button

import android.widget.SearchView
import android.widget.Spinner
import androidx.activity.viewModels

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidlab3.MyApplication
import com.example.androidlab3.domain.AutoEntity
import com.example.androidlab3.R
import com.example.androidlab3.presenters.AutoViewModel
import com.example.androidlab3.presenters.AutoViewModelFactory

class MainActivity : AppCompatActivity() {
    private val viewModel: AutoViewModel by viewModels {
        AutoViewModelFactory((application as MyApplication).autoRepository)
    }

    private lateinit var autos: List<AutoEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = AutoRecyclerAdapter{viewModel.deleteFew(it)}

        // Setting the recyclerview and sending data to it.
        // LiveData observer for the data coming from the viewmodel
        viewModel.allAutos.observe(this) { items ->
            autos = items
            (recyclerView.adapter as AutoRecyclerAdapter).submitListNewData(autos)
            (recyclerView.adapter as AutoRecyclerAdapter).filterModel(null, true)
        }

        val searchView: SearchView = findViewById(R.id.searchView)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                (recyclerView.adapter as AutoRecyclerAdapter).filterModel(query)
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                (recyclerView.adapter as AutoRecyclerAdapter).filterModel(query)
                return false
            }
        })

        val buttonGenerate: Button = findViewById(R.id.buttonGenerate)
        buttonGenerate.setOnClickListener {
            viewModel.generateRandomData(1)
        }

        val buttonAdd: Button = findViewById(R.id.buttonAdd)

        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val query = spinner.selectedItem.toString()
                val fuelQuery = if (query != "Все")
                    query
                else
                    null

                (recyclerView.adapter as AutoRecyclerAdapter).filterFuel(fuelQuery)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
