package com.example.androidlab3.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "autos")
data class AutoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,

    @ColumnInfo(name = "model_name") val modelName: String,
    @ColumnInfo(name = "prod_year") val prodYear: Int,
    @ColumnInfo(name = "fuel_type") val fuelType: String,
    @ColumnInfo(name = "power_capacity") val powerCapacity: Int,
    @ColumnInfo(name = "odometer") val odometerValue: Int,
)

