package com.weappsinc.watertracker.app.feature.water.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/** Ngày (epoch) user đã mở app — dùng tính streak, không phụ thuộc đủ mục tiêu nước. */
@Entity(tableName = "water_app_visit_day")
data class WaterAppVisitDayEntity(@PrimaryKey @ColumnInfo(name = "epoch_day") val epochDay: Long)
