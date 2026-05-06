package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterUnit
import com.weappsinc.watertracker.app.feature.water.domain.util.WaterStreakCalculator
import com.weappsinc.watertracker.app.feature.water.presentation.state.WaterTrackerUiState
import com.weappsinc.watertracker.app.feature.water.presentation.state.WeekDayRingUi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

object WaterTrackerUiMapper {
    fun buildState(
        zone: ZoneId,
        firstInstallEpochDay: Long?,
        goalMl: Int?,
        unit: WaterUnit?,
        intakeByEpochDay: Map<Long, Int>
    ): WaterTrackerUiState {
        val today = LocalDate.now(zone)
        val todayEpoch = today.toEpochDay()
        val installEpoch = firstInstallEpochDay ?: todayEpoch
        val goal = goalMl?.coerceAtLeast(0) ?: 0
        val u = unit ?: WaterUnit.ML
        val todayIntake = intakeByEpochDay[todayEpoch] ?: 0
        val fraction = if (goal > 0) (todayIntake.toFloat() / goal).coerceIn(0f, 1f) else 0f
        val percent = (fraction * 100f).toInt().coerceIn(0, 100)

        val lookup: (Long) -> Int = { d -> intakeByEpochDay[d] ?: 0 }
        val streak = WaterStreakCalculator.computeForDisplay(
            todayEpochDay = todayEpoch,
            firstInstallEpochDay = installEpoch,
            goalMl = goal,
            intakeMlForDay = lookup
        )

        val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val rings = (0L until 7L).map { offset ->
            val date = monday.plusDays(offset)
            val ep = date.toEpochDay()
            val intake = lookup(ep)
            val prog = if (goal > 0) (intake.toFloat() / goal).coerceIn(0f, 1f) else 0f
            WeekDayRingUi(
                label = AppText.WEEKDAY_LABELS_VN[offset.toInt()],
                epochDay = ep,
                progress = prog,
                isToday = ep == todayEpoch,
                beforeInstall = ep < installEpoch
            )
        }

        return WaterTrackerUiState(
            goalMl = goal,
            unit = u,
            todayIntakeMl = todayIntake,
            progressFraction = fraction,
            progressPercent = percent,
            streakDays = streak,
            weekRings = rings
        )
    }
}
