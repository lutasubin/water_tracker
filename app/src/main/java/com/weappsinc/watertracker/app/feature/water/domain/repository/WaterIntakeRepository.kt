package com.weappsinc.watertracker.app.feature.water.domain.repository

import kotlinx.coroutines.flow.Flow

/** Nguồn sự thật cho tổng ml uống theo ngày (Room). */
interface WaterIntakeRepository {
    fun observeTotalMlForDay(epochDay: Long): Flow<Int>
    fun observeTotalsBetween(startEpochDay: Long, endEpochDayInclusive: Long): Flow<Map<Long, Int>>
    suspend fun addMl(epochDay: Long, deltaMl: Int)
}
