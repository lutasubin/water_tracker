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
    /** Tổng ml trong ngày (DB) — dùng cho pháo hoa / baseline. */
    val todayTotalIntakeMl: Int,
    /** ml hiển thị tiến độ (sau trừ baseline khi đã đóng popup đạt mục tiêu). */
    val todayIntakeMl: Int,
    val progressFraction: Float,
    val progressPercent: Int,
    val streakDays: Int,
    val weekRings: List<WeekDayRingUi>
)
