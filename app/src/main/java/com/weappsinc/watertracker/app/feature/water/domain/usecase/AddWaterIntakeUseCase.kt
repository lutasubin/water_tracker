package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository

class AddWaterIntakeUseCase(
    private val intakeRepository: WaterIntakeRepository
) {
    suspend operator fun invoke(
        epochDay: Long,
        amountMl: Int,
        timestampMs: Long = System.currentTimeMillis()
    ) {
        intakeRepository.addWaterIntakeWithLog(epochDay, timestampMs, amountMl)
    }
}
