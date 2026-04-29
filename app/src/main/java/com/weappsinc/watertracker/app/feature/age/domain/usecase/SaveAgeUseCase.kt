package com.weappsinc.watertracker.app.feature.age.domain.usecase

import com.weappsinc.watertracker.app.feature.age.domain.repository.AgeRepository

class SaveAgeUseCase(private val repository: AgeRepository) {
    suspend operator fun invoke(age: Int) {
        repository.saveAge(age)
    }
}
