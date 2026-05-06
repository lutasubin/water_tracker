package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterPreferencesRepository
import kotlinx.coroutines.flow.Flow

/** Ngày bắt đầu dùng app (epoch day) — dùng chỏ cửa sổ biểu đồ weigh, streak, v.v. */
class ObserveFirstInstallEpochDayUseCase(
    private val prefs: WaterPreferencesRepository
) {
    operator fun invoke(): Flow<Long?> = prefs.observeFirstInstallEpochDay()
}
