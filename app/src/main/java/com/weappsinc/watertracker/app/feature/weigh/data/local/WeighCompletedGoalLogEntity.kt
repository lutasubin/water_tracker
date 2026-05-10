package com.weappsinc.watertracker.app.feature.weigh.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weigh_completed_goal_log",
    foreignKeys = [
        ForeignKey(
            entity = WeighCompletedGoalEntity::class,
            parentColumns = ["id"],
            childColumns = ["goalId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["goalId"])],
)
data class WeighCompletedGoalLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val goalId: Long,
    val epochDay: Long,
    val weightKg: Double,
    val recordedAtMs: Long,
)
