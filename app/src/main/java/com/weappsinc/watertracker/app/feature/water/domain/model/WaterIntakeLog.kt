package com.weappsinc.watertracker.app.feature.water.domain.model

/** Một log uống nước (domain). */
data class WaterIntakeLog(
    val id: Long,
    val epochDay: Long,
    val timestampMs: Long,
    val amountMl: Int
)
