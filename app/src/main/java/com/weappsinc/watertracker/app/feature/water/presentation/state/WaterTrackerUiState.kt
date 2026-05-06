package com.weappsinc.watertracker.app.feature.water.presentation.state

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import java.time.DayOfWeek

data class WeekDayRingUi(
    val dayOfWeek: DayOfWeek,
    val epochDay: Long,
    val progress: Float,
    val isToday: Boolean,
    val beforeInstall: Boolean
)

data class WaterTrackerUiState(
    val goalMl: Int,
    val unit: WaterUnit,
    val todayIntakeMl: Int,
    val progressFraction: Float,
    val progressPercent: Int,
    val streakDays: Int,
    val weekRings: List<WeekDayRingUi>
)
