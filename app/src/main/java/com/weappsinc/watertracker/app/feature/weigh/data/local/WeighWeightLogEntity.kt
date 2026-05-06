package com.weappsinc.watertracker.app.feature.weigh.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weigh_weight_log")
data class WeighWeightLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val epochDay: Long,
    val weightKg: Double,
    val recordedAtMs: Long
)
