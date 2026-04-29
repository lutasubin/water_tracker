package com.weappsinc.watertracker.app.feature.weight.domain.usecase

import com.weappsinc.watertracker.app.feature.weight.domain.repository.WeightRepository

class SaveWeightUseCase(private val repository: WeightRepository) {
    suspend operator fun invoke(weight: Int) {
        repository.saveWeight(weight)
    }
}
