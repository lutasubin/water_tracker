package com.weappsinc.watertracker.app.feature.water.domain.repository

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import kotlinx.coroutines.flow.Flow

/** Cài đặt nhẹ: ngày cài app, goal đã lưu, đơn vị hiển thị. */
interface WaterPreferencesRepository {
    suspend fun ensureFirstInstallEpochDayRecorded()
    fun observeFirstInstallEpochDay(): Flow<Long?>
    suspend fun saveGoalMlAndUnit(goalMl: Int, unit: WaterUnit)
    fun observeSavedGoalMl(): Flow<Int?>
    fun observeSavedUnit(): Flow<WaterUnit?>
    fun observeGoalDoneDialogShownEpochDay(): Flow<Long?>
    suspend fun saveGoalDoneDialogShownEpochDay(epochDay: Long)
}
