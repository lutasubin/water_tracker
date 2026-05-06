package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import java.time.LocalDate
import java.time.ZoneId

class SaveWeighLogUseCase(
    private val repository: WeighLogRepository
) {
    suspend operator fun invoke(weightKg: Float): Result<Unit> {
        val day = LocalDate.now(ZoneId.systemDefault()).toEpochDay()
        return repository.insertLog(
            epochDay = day,
            weightKg = weightKg.toDouble(),
            recordedAtMs = System.currentTimeMillis()
        )
    }
}
