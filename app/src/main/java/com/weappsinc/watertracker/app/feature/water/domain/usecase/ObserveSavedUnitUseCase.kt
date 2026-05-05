package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveSavedUnitUseCase(
    private val prefs: WaterPreferencesRepository
) {
    operator fun invoke(): Flow<WaterUnit?> = prefs.observeSavedUnit()
}
