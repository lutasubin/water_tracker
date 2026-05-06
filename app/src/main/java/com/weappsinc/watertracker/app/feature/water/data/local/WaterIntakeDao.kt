package com.weappsinc.watertracker.app.feature.water.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterIntakeDao {

    @Query("SELECT total_ml FROM daily_water_total WHERE epoch_day = :epochDay")
    fun observeTotalMl(epochDay: Long): Flow<Int?>

    @Query("SELECT * FROM daily_water_total WHERE epoch_day BETWEEN :start AND :endInclusive")
    fun observeTotalsInRange(start: Long, endInclusive: Long): Flow<List<DailyWaterTotalEntity>>

    @Query("SELECT total_ml FROM daily_water_total WHERE epoch_day = :epochDay")
    suspend fun getTotalMl(epochDay: Long): Int?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entity: DailyWaterTotalEntity)

    @Insert
    suspend fun insertLog(entity: WaterIntakeLogEntity): Long

    @Query(
        "SELECT * FROM water_intake_log WHERE epoch_day = :epochDay ORDER BY timestamp_ms DESC"
    )
    fun observeLogsForDay(epochDay: Long): Flow<List<WaterIntakeLogEntity>>

    @Transaction
    suspend fun addWaterIntakeWithLog(epochDay: Long, timestampMs: Long, amountMl: Int): Int {
        insertLog(
            WaterIntakeLogEntity(
                epochDay = epochDay,
                timestampMs = timestampMs,
                amountMl = amountMl
            )
        )
        val current = getTotalMl(epochDay) ?: 0
        val next = current + amountMl
        upsert(DailyWaterTotalEntity(epochDay, next))
        return next
    }

    /** Cộng thêm ml (legacy / migration thuần tổng). */
    @Transaction
    suspend fun addMl(epochDay: Long, deltaMl: Int): Int {
        val current = getTotalMl(epochDay) ?: 0
        val next = current + deltaMl
        upsert(DailyWaterTotalEntity(epochDay, next))
        return next
    }
}
