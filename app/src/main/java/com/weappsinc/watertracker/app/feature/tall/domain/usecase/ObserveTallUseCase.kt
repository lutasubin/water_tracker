package com.weappsinc.watertracker.app.feature.tall.domain.usecase

import com.weappsinc.watertracker.app.feature.tall.domain.repository.TallRepository

class ObserveTallUseCase(private val repository: TallRepository) {
    operator fun invoke() = repository.observeTall()
}
