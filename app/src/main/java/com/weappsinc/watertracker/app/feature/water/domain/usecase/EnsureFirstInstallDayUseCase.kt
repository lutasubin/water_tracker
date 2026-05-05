package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository

class EnsureFirstInstallDayUseCase(
    private val prefs: WaterPreferencesRepository
) {
    suspend operator fun invoke() {
        prefs.ensureFirstInstallEpochDayRecorded()
    }
}
