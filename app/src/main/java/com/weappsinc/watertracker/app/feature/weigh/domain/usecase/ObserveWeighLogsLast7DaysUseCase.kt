package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

/** Quan sát toàn bộ bản ghi trong 7 ngày lịch gần nhất (kể cả nhiều lần/ngày), mới nhất trước. */
class ObserveWeighLogsLast7DaysUseCase(
    private val repository: WeighLogRepository
) {
    operator fun invoke(): Flow<List<WeighLogEntry>> {
        val zone = ZoneId.systemDefault()
        val end = LocalDate.now(zone).toEpochDay()
        val start = end - 6L
        return repository.observeLogsBetweenEpochDays(start, end)
    }
}
