package com.weappsinc.watertracker.app.feature.weigh.domain.model

/** Một lần ghi nhận cân nặng (lịch sử). */
data class WeighLogEntry(
    val weightKg: Double,
    val recordedAtMs: Long,
    val epochDay: Long
)
