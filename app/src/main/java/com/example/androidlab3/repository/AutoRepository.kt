package com.example.androidlab3.repository

import com.example.androidlab3.db.AutoLocalSource
import com.example.androidlab3.domain.AutoEntity
import kotlinx.coroutines.flow.Flow

class AutoRepository(private val autoSource: AutoLocalSource) {
    //Get the data from the DB
    val allAutos: Flow<List<AutoEntity>> = autoSource.getAll()

    //Insert the element to the DB
    fun getModelLike(modelName: String): List<AutoEntity> {
        return autoSource.getModelLike(modelName)
    }

    suspend fun updateModel(autoId: Int, newModel: String) {
        autoSource.updateModel(autoId = autoId, newModel = newModel)
    }

    fun insertFew(vararg auto: AutoEntity) {
        autoSource.insertFew(*auto)
    }

    fun deleteFew(vararg autoId: Int) {
        autoSource.deleteFew(*autoId)
    }
}
