package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterIntakeRepository

class AddWaterIntakeUseCase(
    private val intakeRepository: WaterIntakeRepository
) {
    suspend operator fun invoke(epochDay: Long, deltaMl: Int) {
        intakeRepository.addMl(epochDay, deltaMl)
    }
}
