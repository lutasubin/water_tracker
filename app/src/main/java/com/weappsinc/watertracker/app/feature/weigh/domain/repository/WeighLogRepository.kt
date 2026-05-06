package com.weappsinc.watertracker.app.feature.weigh.domain.repository

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import kotlinx.coroutines.flow.Flow

interface WeighLogRepository {
    fun observeLatestTwoDesc(): Flow<List<WeighLogEntry>>
    fun observeLatestLog(): Flow<WeighLogEntry?>
    fun observeLatestForEpochDay(epochDay: Long): Flow<WeighLogEntry?>
    fun observeLogsBetweenEpochDays(startEpochDay: Long, endEpochDay: Long): Flow<List<WeighLogEntry>>
    suspend fun insertLog(epochDay: Long, weightKg: Double, recordedAtMs: Long): Result<Unit>
}
