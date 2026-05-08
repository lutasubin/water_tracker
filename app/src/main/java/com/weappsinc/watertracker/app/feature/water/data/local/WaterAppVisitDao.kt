package com.weappsinc.watertracker.app.feature.water.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterAppVisitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: WaterAppVisitDayEntity)

    @Query(
        "SELECT epoch_day FROM water_app_visit_day WHERE epoch_day BETWEEN :start AND :endInclusive ORDER BY epoch_day ASC"
    )
    fun observeEpochDaysBetween(start: Long, endInclusive: Long): Flow<List<Long>>
}
