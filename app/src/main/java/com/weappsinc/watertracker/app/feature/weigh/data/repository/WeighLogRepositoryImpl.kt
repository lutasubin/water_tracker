package com.weappsinc.watertracker.app.feature.weigh.data.repository

import com.weappsinc.watertracker.app.feature.weigh.data.local.WeighWeightLogDao
import com.weappsinc.watertracker.app.feature.weigh.data.local.WeighWeightLogEntity
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WeighLogRepositoryImpl(
    private val dao: WeighWeightLogDao
) : WeighLogRepository {

    override fun observeLatestTwoDesc(): Flow<List<WeighLogEntry>> =
        dao.observeLatestTwoDesc().map { list -> list.map { it.toDomain() } }

    override fun observeLatestLog(): Flow<WeighLogEntry?> =
        dao.observeLatestOneDesc().map { entity -> entity?.toDomain() }

    override fun observeLatestForEpochDay(epochDay: Long): Flow<WeighLogEntry?> =
        dao.observeLatestForEpochDay(epochDay).map { it?.toDomain() }

    override fun observeLogsBetweenEpochDays(startEpochDay: Long, endEpochDay: Long): Flow<List<WeighLogEntry>> =
        dao.observeLogsBetweenEpochDays(startEpochDay, endEpochDay).map { list ->
            list.map { it.toDomain() }
        }

    override suspend fun insertLog(epochDay: Long, weightKg: Double, recordedAtMs: Long): Result<Unit> =
        runCatching {
            dao.insert(
                WeighWeightLogEntity(
                    epochDay = epochDay,
                    weightKg = weightKg,
                    recordedAtMs = recordedAtMs
                )
            )
            Unit
        }

    private fun WeighWeightLogEntity.toDomain() = WeighLogEntry(
        weightKg = weightKg,
        recordedAtMs = recordedAtMs,
        epochDay = epochDay
    )
}
