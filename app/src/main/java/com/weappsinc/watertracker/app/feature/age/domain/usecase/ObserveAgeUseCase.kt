package com.weappsinc.watertracker.app.feature.age.domain.usecase

import com.weappsinc.watertracker.app.feature.age.domain.repository.AgeRepository

class ObserveAgeUseCase(private val repository: AgeRepository) {
    operator fun invoke() = repository.observeAge()
}
