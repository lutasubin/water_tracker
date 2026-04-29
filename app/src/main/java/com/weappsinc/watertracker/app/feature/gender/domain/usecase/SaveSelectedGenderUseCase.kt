package com.weappsinc.watertracker.app.feature.gender.domain.usecase

import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.gender.domain.repository.GenderRepository

class SaveSelectedGenderUseCase(
    private val repository: GenderRepository
) {
    suspend operator fun invoke(gender: GenderType) {
        repository.saveSelectedGender(gender)
    }
}
