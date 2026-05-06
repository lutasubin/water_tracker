package com.weappsinc.watertracker.app.feature.water.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Mỗi lần user ghi nhận uống nước (để báo cáo ngày + danh sách Record). */
@Entity(tableName = "water_intake_log")
data class WaterIntakeLogEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "epoch_day") val epochDay: Long,
    @ColumnInfo(name = "timestamp_ms") val timestampMs: Long,
    @ColumnInfo(name = "amount_ml") val amountMl: Int
)
