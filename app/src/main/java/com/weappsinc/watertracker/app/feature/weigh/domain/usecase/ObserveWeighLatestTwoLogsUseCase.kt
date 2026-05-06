package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import kotlinx.coroutines.flow.Flow

class ObserveWeighLatestTwoLogsUseCase(
    private val repository: WeighLogRepository
) {
    operator fun invoke(): Flow<List<WeighLogEntry>> = repository.observeLatestTwoDesc()
}
