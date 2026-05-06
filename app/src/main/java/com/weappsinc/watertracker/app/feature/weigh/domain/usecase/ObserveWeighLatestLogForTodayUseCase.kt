package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

class ObserveWeighLatestLogForTodayUseCase(
    private val repository: WeighLogRepository
) {
    operator fun invoke(): Flow<WeighLogEntry?> {
        val day = LocalDate.now(ZoneId.systemDefault()).toEpochDay()
        return repository.observeLatestForEpochDay(day)
    }
}
