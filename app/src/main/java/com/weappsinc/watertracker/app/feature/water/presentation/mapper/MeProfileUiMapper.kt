package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.feature.water.domain.util.WaterStreakCalculator
import com.weappsinc.watertracker.app.feature.water.presentation.state.MeProfileUiState
import java.time.LocalDate
import java.time.ZoneId

object MeProfileUiMapper {

    fun build(
        zone: ZoneId,
        firstInstallEpochDay: Long?,
        goalMl: Int?,
        intakeByEpochDay: Map<Long, Int>,
    ): MeProfileUiState {
        val today = LocalDate.now(zone).toEpochDay()
        val install = firstInstallEpochDay ?: today
        val goal = goalMl?.coerceAtLeast(0) ?: 0
        val totalMl = intakeByEpochDay.values.sum()
        val lookup: (Long) -> Int = { d -> intakeByEpochDay[d] ?: 0 }
        val streak = WaterStreakCalculator.computeForDisplay(
            todayEpochDay = today,
            firstInstallEpochDay = install,
            goalMl = goal,
            intakeMlForDay = lookup
        )
        return MeProfileUiState(
            totalDrinkingMl = totalMl,
            streakDays = streak,
        )
    }
}
