package com.weappsinc.watertracker.app.feature.water.domain.usecase

import com.weappsinc.watertracker.app.feature.water.domain.repository.WaterAppVisitRepository
import java.time.LocalDate
import java.time.ZoneId

/** Ghi nhận đã mở app vào đúng ngày máy — gọi từ Splash sau khi ensureFirstInstall. */
class RecordWaterAppOpenDayUseCase(
    private val visitRepository: WaterAppVisitRepository,
    private val zone: ZoneId = ZoneId.systemDefault()
) {
    suspend operator fun invoke() {
        val epoch = LocalDate.now(zone).toEpochDay()
        visitRepository.recordOpenOnEpochDay(epoch)
    }
}
