package com.weappsinc.watertracker.app.feature.weigh.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weigh_completed_goal")
data class WeighCompletedGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val targetWeightKg: Double,
    val journeyStartWeightKg: Double,
    val achievedBodyWeightKg: Double,
    val completedAtEpochDay: Long,
    val completedAtMs: Long,
)
