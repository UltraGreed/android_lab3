package com.example.androidlab3.db

import com.example.androidlab3.domain.AutoEntity
import kotlinx.coroutines.flow.Flow

class AutoLocalSource(private val autoDao: AutoDao) {
    fun getAll(): Flow<List<AutoEntity>> {
        return autoDao.getAll()
    }

    fun getModelLike(modelName: String): List<AutoEntity> {
        return autoDao.getModelLike(modelName)
    }

    suspend fun updateModel(autoId: Int, newModel: String) {
        autoDao.updateModel(autoId = autoId, newModel = newModel)
    }

    fun insertFew(vararg auto: AutoEntity) {
        autoDao.insertFew(*auto)
    }

    fun deleteFew(vararg autoId: Int) {
        autoDao.deleteFew(*autoId)
    }
}
