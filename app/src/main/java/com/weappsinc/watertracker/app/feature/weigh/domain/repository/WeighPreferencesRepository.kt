package com.weappsinc.watertracker.app.feature.weigh.domain.repository

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import kotlinx.coroutines.flow.Flow

/** Cài đặt màn Weigh: đơn vị khối lượng, cân mục tiêu (kg), mốc bắt đầu hành trình. */
interface WeighPreferencesRepository {
    fun observeMassUnit(): Flow<MassUnit>
    suspend fun saveMassUnit(unit: MassUnit)
    fun observeTargetWeightKg(): Flow<Float?>
    suspend fun saveTargetWeightKg(weightKg: Float?)
    fun observeJourneyStartWeightKg(): Flow<Float?>
    suspend fun saveJourneyStartWeightKg(weightKg: Float?)
}
