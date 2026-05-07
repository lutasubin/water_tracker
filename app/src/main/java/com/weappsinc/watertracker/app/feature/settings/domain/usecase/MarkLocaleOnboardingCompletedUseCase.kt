package com.weappsinc.watertracker.app.feature.settings.domain.usecase

import com.weappsinc.watertracker.app.feature.settings.domain.repository.LocalePreferencesRepository

class MarkLocaleOnboardingCompletedUseCase(
    private val repository: LocalePreferencesRepository,
) {
    suspend operator fun invoke() {
        repository.markLocaleOnboardingCompleted()
    }
}
