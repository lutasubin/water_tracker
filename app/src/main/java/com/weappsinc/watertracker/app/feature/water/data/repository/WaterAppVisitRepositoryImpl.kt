package com.weappsinc.watertracker.app.feature.water.data.repository

import com.weappsinc.watertracker.app.feature.water.data.local.WaterAppVisitDao
import com.weappsinc.watertracker.app.feature.water.data.local.WaterAppVisitDayEntity
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterAppVisitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class WaterAppVisitRepositoryImpl(private val dao: WaterAppVisitDao) : WaterAppVisitRepository {

    override suspend fun recordOpenOnEpochDay(epochDay: Long) {
        dao.insert(WaterAppVisitDayEntity(epochDay))
    }

    override fun observeOpenEpochDaysBetween(startEpochDay: Long, endEpochDayInclusive: Long): Flow<Set<Long>> =
        dao.observeEpochDaysBetween(startEpochDay, endEpochDayInclusive).map { it.toSet() }
}
