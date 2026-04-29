package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterGoalRepository

class ObserveWaterGoalMlUseCase(
    private val repository: WaterGoalRepository
) {
    operator fun invoke() = repository.observeDailyGoalMl()
}

