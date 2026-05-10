package com.weappsinc.watertracker.app.feature.weigh.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeighWeightLogDao {

    @Insert
    suspend fun insert(entity: WeighWeightLogEntity): Long

    @Query(
        """
        SELECT * FROM weigh_weight_log
        ORDER BY recordedAtMs DESC
        LIMIT 2
        """
    )
    fun observeLatestTwoDesc(): Flow<List<WeighWeightLogEntity>>

    @Query(
        """
        SELECT * FROM weigh_weight_log
        ORDER BY recordedAtMs DESC
        LIMIT 1
        """
    )
    fun observeLatestOneDesc(): Flow<WeighWeightLogEntity?>

    @Query(
        """
        SELECT * FROM weigh_weight_log
        WHERE epochDay = :epochDay
        ORDER BY recordedAtMs DESC
        LIMIT 1
        """
    )
    fun observeLatestForEpochDay(epochDay: Long): Flow<WeighWeightLogEntity?>

    @Query(
        """
        SELECT * FROM weigh_weight_log
        WHERE epochDay >= :startEpochDay AND epochDay <= :endEpochDay
        ORDER BY recordedAtMs DESC
        """
    )
    fun observeLogsBetweenEpochDays(startEpochDay: Long, endEpochDay: Long): Flow<List<WeighWeightLogEntity>>
}
