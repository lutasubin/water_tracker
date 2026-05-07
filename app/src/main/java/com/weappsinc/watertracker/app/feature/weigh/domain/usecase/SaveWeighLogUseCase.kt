package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.exception.WeighDayAlreadyLoggedException
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import java.time.LocalDate
import java.time.ZoneId

class SaveWeighLogUseCase(
    private val repository: WeighLogRepository
) {
    suspend operator fun invoke(weightKg: Float): Result<Unit> {
        val day = LocalDate.now(ZoneId.systemDefault()).toEpochDay()
        if (repository.countLogsForEpochDay(day) > 0) {
            return Result.failure(WeighDayAlreadyLoggedException())
        }
        return repository.insertLog(
            epochDay = day,
            weightKg = weightKg.toDouble(),
            recordedAtMs = System.currentTimeMillis()
        )
    }
}
