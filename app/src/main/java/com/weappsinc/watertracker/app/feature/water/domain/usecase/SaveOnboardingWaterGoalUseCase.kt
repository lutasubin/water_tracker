package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository

class SaveOnboardingWaterGoalUseCase(
    private val prefs: WaterPreferencesRepository
) {
    suspend operator fun invoke(goalMl: Int, unit: WaterUnit) {
        prefs.saveGoalMlAndUnit(goalMl, unit)
    }
}
