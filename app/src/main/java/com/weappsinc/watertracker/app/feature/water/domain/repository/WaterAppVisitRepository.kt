package com.weappsinc.watertracker.app.feature.water.domain.repository

import kotlinx.coroutines.flow.Flow

interface WaterAppVisitRepository {
    suspend fun recordOpenOnEpochDay(epochDay: Long)

    /** Tập các ngày đã mở app trong khoảng (để mapper streak). */
    fun observeOpenEpochDaysBetween(startEpochDay: Long, endEpochDayInclusive: Long): Flow<Set<Long>>
}
