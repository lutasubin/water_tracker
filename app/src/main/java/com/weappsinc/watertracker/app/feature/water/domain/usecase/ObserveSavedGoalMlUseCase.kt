package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveSavedGoalMlUseCase(
    private val prefs: WaterPreferencesRepository
) {
    operator fun invoke(): Flow<Int?> = prefs.observeSavedGoalMl()
}
