package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighPreferencesRepository

class SaveWeighTargetWeightKgUseCase(
    private val repository: WeighPreferencesRepository
) {
    suspend operator fun invoke(weightKg: Float?) = repository.saveTargetWeightKg(weightKg)
}
