package com.weappsinc.watertracker.app.feature.water.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Một dòng = tổng ml đã uống trong ngày (theo epoch day). */
@Entity(tableName = "daily_water_total")
data class DailyWaterTotalEntity(
    @PrimaryKey @ColumnInfo(name = "epoch_day") val epochDay: Long,
    @ColumnInfo(name = "total_ml") val totalMl: Int
)
