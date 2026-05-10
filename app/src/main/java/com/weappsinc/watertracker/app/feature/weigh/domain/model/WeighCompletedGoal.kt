package com.weappsinc.watertracker.app.feature.weigh.domain.model

/** Bản ghi hành trình cân đã đạt mục tiêu (lưu Room). */
data class WeighCompletedGoal(
    val id: Long,
    val targetWeightKg: Double,
    val journeyStartWeightKg: Double,
    val achievedBodyWeightKg: Double,
    val completedAtEpochDay: Long,
    val completedAtMs: Long,
)
