package com.weappsinc.watertracker.app.feature.weigh.domain.model

/** Một điểm trên biểu đồ xu hướng 7 ngày (mỗi ngày một giá trị kg). */
data class WeighHistoryChartPoint(
    val epochDay: Long,
    val weightKg: Float
)
