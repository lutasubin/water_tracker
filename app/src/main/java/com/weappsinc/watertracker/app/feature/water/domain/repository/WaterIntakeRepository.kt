package com.weappsinc.watertracker.app.feature.water.domain.repository

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterIntakeLog
import kotlinx.coroutines.flow.Flow

/** Nguồn sự thật cho tổng ml uống theo ngày (Room). */
interface WaterIntakeRepository {
    fun observeTotalMlForDay(epochDay: Long): Flow<Int>
    fun observeTotalsBetween(startEpochDay: Long, endEpochDayInclusive: Long): Flow<Map<Long, Int>>
    fun observeLogsForDay(epochDay: Long): Flow<List<WaterIntakeLog>>
    /** Ghi log + cộng daily_water_total trong một transaction. */
    suspend fun addWaterIntakeWithLog(epochDay: Long, timestampMs: Long, amountMl: Int)
}
