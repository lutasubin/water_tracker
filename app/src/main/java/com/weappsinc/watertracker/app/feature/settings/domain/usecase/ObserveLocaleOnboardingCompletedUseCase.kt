package com.weappsinc.watertracker.app.feature.settings.domain.usecase

import com.weappsinc.watertracker.app.feature.settings.domain.repository.LocalePreferencesRepository
import kotlinx.coroutines.flow.Flow

class ObserveLocaleOnboardingCompletedUseCase(
    private val repository: LocalePreferencesRepository,
) {
    operator fun invoke(): Flow<Boolean> = repository.observeLocaleOnboardingCompleted()
}
