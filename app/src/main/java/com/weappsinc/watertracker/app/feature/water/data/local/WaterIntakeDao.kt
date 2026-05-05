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

    /** Cộng thêm ml cho một ngày (epoch day). Trả về tổng ml sau khi cộng (tránh KSP lỗi chữ ký Unit). */
    @Transaction
    suspend fun addMl(epochDay: Long, deltaMl: Int): Int {
        val current = getTotalMl(epochDay) ?: 0
        val next = current + deltaMl
        upsert(DailyWaterTotalEntity(epochDay, next))
        return next
    }
}
