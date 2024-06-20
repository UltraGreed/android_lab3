package com.example.androidlab3.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.androidlab3.domain.AutoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AutoDao {
    @Query("SELECT * FROM autos")
    fun getAll(): Flow<List<AutoEntity>>

    @Query("SELECT * FROM autos WHERE autos.model_name LIKE :modelName")
    fun getModelLike(modelName: String): List<AutoEntity>

    @Query("UPDATE autos SET model_name = :newModel WHERE id = :autoId")
    suspend fun updateModel(autoId: Int, newModel: String)

    @Insert
    fun insertFew(vararg autos: AutoEntity)

    @Query("DELETE FROM autos WHERE id IN (:ids)")
    fun deleteFew(vararg ids: Int)
}
