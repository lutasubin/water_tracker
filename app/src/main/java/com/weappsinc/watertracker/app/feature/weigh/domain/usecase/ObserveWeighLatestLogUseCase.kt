package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.repository.WeighLogRepository
import kotlinx.coroutines.flow.Flow

/** Bản ghi cân mới nhất theo thời gian — dùng để đồng bộ “cân hiện tại” sau khi ghi nhận. */
class ObserveWeighLatestLogUseCase(private val repository: WeighLogRepository) {
    operator fun invoke(): Flow<WeighLogEntry?> = repository.observeLatestLog()
}
