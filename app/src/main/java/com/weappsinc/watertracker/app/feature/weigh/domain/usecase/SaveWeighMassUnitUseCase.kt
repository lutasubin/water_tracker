package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighPreferencesRepository

class SaveWeighMassUnitUseCase(
    private val repository: WeighPreferencesRepository
) {
    suspend operator fun invoke(unit: MassUnit) = repository.saveMassUnit(unit)
}
