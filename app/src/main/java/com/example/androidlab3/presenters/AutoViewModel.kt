package com.example.androidlab3.presenters

import androidx.lifecycle.*
import com.example.androidlab3.domain.AutoEntity
import com.example.androidlab3.repository.AutoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AutoViewModel(private val repository: AutoRepository) : ViewModel() {
    val allAutos: LiveData<List<AutoEntity>> = repository.allAutos.asLiveData()

    //Insert the element to the DB
    fun getModelLike(modelName: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.getModelLike(modelName)
        }
    }

    suspend fun updateModel(autoId: Int, newModel: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.updateModel(autoId = autoId, newModel = newModel)
        }
    }

    fun insertFew(vararg auto: AutoEntity) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.insertFew(*auto)
        }
    }

    fun deleteFew(vararg autoId: Int) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            repository.deleteFew(*autoId)
        }
    }

    fun generateRandomData(n: Int) {
        val toyotaCrown = "Toyota Crown"
        val toyotaCruiser = "Toyota Land Cruiser 100"
        val toyotaMark2 = "Toyota Mark II"
        val toyotaPrius = "Toyota Prius"
        val toyotaBz4x = "Toyota bZ4X"

        val petrol = "Бензин"
        val diesel = "Дизель"
        val hybrid = "Гибрид"
        val electric = "Электро"

        val modelFuelMap = mapOf(
            toyotaCrown to listOf(petrol, diesel),
            toyotaCruiser to listOf(petrol, diesel),
            toyotaMark2 to listOf(petrol, diesel),
            toyotaPrius to listOf(hybrid),
            toyotaBz4x to listOf(electric)
        )
        val modelYearMap = mapOf(
            toyotaCrown to 1988..2024,
            toyotaCruiser to 1997..2008,
            toyotaMark2 to 1988..2005,
            toyotaPrius to 1997..2024,
            toyotaBz4x to 2022..2024
        )
        val modelPowerMap = mapOf(
            toyotaCrown to 100..250,
            toyotaCruiser to 200..450,
            toyotaMark2 to 140..230,
            toyotaPrius to 76..100,
            toyotaBz4x to 200..220
        )

        val newList = mutableListOf<AutoEntity>()

        for (i in 0..<n) {
            val model = modelFuelMap.keys.random()
            val year = modelYearMap[model]!!.random()
            val fuel = modelFuelMap[model]!!.random()
            val odometer = (50..150).random() + (2024 - year) * 600 / (2024 - 1988)
            val power = modelPowerMap[model]!!.random()

            newList.add(
                AutoEntity(
                    modelName = model,
                    prodYear = year,
                    fuelType = fuel,
                    odometerValue = odometer,
                    powerCapacity = power
                )
            )
        }

        insertFew(*newList.toTypedArray())
    }
}

// Override ViewModelProvider.NewInstanceFactory to create the ViewModel (VM).
class AutoViewModelFactory(private val repository: AutoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AutoViewModel(repository) as T
    }
}
