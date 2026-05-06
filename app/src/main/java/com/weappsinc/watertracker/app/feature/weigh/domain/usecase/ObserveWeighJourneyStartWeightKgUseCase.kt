package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighPreferencesRepository

class ObserveWeighJourneyStartWeightKgUseCase(
    private val repository: WeighPreferencesRepository
) {
    operator fun invoke() = repository.observeJourneyStartWeightKg()
}
