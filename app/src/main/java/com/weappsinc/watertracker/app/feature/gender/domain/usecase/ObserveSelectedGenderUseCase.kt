package com.weappsinc.watertracker.app.feature.gender.domain.usecase

import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.gender.domain.repository.GenderRepository
import kotlinx.coroutines.flow.Flow

class ObserveSelectedGenderUseCase(
    private val repository: GenderRepository
) {
    operator fun invoke(): Flow<GenderType> = repository.observeSelectedGender()
}
