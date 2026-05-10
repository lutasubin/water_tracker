package com.weappsinc.watertracker.app.feature.weigh.domain.model

/** Snapshot khi user đóng popup đạt mục tiêu — đưa vào archive + xóa prefs. */
data class WeightGoalCompletionSnapshot(
    val targetWeightKg: Float,
    val journeyStartWeightKg: Float,
    val achievedBodyWeightKg: Float,
    val completedAtEpochDay: Long,
    val completedAtMs: Long,
)
