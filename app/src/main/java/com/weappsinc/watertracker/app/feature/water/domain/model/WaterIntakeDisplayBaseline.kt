package com.weappsinc.watertracker.app.feature.water.domain.model

/** Baseline tổng ml trong ngày tại thời điểm user đóng popup đạt mục tiêu — UI tiến độ tính từ (total − baseline). */
data class WaterIntakeDisplayBaseline(
    val epochDay: Long,
    val totalMlAtReset: Int,
)
