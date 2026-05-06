package com.weappsinc.watertracker.app.feature.water.data.repository

import com.weappsinc.watertracker.app.feature.water.data.local.WaterIntakeDao
import com.weappsinc.watertracker.app.feature.water.data.local.WaterIntakeLogEntity
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterIntakeLog
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WaterIntakeRepositoryImpl(
    private val dao: WaterIntakeDao
) : WaterIntakeRepository {

    override fun observeTotalMlForDay(epochDay: Long): Flow<Int> =
        dao.observeTotalMl(epochDay).map { it ?: 0 }

    override fun observeTotalsBetween(startEpochDay: Long, endEpochDayInclusive: Long): Flow<Map<Long, Int>> =
        dao.observeTotalsInRange(startEpochDay, endEpochDayInclusive).map { list ->
            list.associate { it.epochDay to it.totalMl }
        }

    override fun observeLogsForDay(epochDay: Long): Flow<List<WaterIntakeLog>> =
        dao.observeLogsForDay(epochDay).map { list -> list.map { it.toDomain() } }

    override suspend fun addWaterIntakeWithLog(epochDay: Long, timestampMs: Long, amountMl: Int) {
        dao.addWaterIntakeWithLog(epochDay, timestampMs, amountMl)
    }

    private fun WaterIntakeLogEntity.toDomain(): WaterIntakeLog =
        WaterIntakeLog(id = id, epochDay = epochDay, timestampMs = timestampMs, amountMl = amountMl)
}
