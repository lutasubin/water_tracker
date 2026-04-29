package com.weappsinc.watertracker.app.feature.weight.domain.usecase

import com.weappsinc.watertracker.app.feature.weight.domain.repository.WeightRepository

class ObserveWeightUseCase(private val repository: WeightRepository) {
    operator fun invoke() = repository.observeWeight()
}
