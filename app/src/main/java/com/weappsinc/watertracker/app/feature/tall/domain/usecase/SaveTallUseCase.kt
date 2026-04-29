package com.weappsinc.watertracker.app.feature.tall.domain.usecase

import com.weappsinc.watertracker.app.feature.tall.domain.repository.TallRepository

class SaveTallUseCase(private val repository: TallRepository) {
    suspend operator fun invoke(tall: Int) {
        repository.saveTall(tall)
    }
}
