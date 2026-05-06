package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterIntakeLog
import java.time.Instant
import java.time.ZoneId

/** Gom log trong ngày thành 6 cột 4 giờ (00–04 … 20–24). */
class BuildDayChartBucketsFromLogsUseCase {
    operator fun invoke(logs: List<WaterIntakeLog>, zone: ZoneId): List<Int> {
        val buckets = IntArray(6)
        logs.forEach { log ->
            val hour = Instant.ofEpochMilli(log.timestampMs).atZone(zone).hour
            val idx = hour / 4
            if (idx in 0..5) buckets[idx] += log.amountMl
        }
        return buckets.toList()
    }
}
