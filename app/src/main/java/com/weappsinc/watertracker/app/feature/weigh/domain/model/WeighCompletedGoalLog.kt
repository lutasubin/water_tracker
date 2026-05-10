package com.weappsinc.watertracker.app.feature.weigh.domain.model

data class WeighCompletedGoalLog(
    val epochDay: Long,
    val weightKg: Double,
    val recordedAtMs: Long,
)
