package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.feature.gender.domain.model.GenderType
import com.weappsinc.watertracker.app.feature.water.domain.util.WaterStreakCalculator
import com.weappsinc.watertracker.app.feature.water.presentation.state.MeProfileUiState
import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import java.time.LocalDate
import java.time.ZoneId

object MeProfileUiMapper {

    fun build(
        zone: ZoneId,
        firstInstallEpochDay: Long?,
        intakeByEpochDay: Map<Long, Int>,
        openEpochDays: Set<Long>,
    ): MeProfileUiState {
        val today = LocalDate.now(zone).toEpochDay()
        val install = firstInstallEpochDay ?: today
        val totalMl = intakeByEpochDay.values.sum()
        val streak = WaterStreakCalculator.computeForDisplay(
            todayEpochDay = today,
            firstInstallEpochDay = install,
            openedEpochDays = openEpochDays
        )
        return MeProfileUiState(
            totalDrinkingMl = totalMl,
            streakDays = streak,
            heightValueText = "--",
            weightValueText = "--",
            ageValueText = "--",
            sex = GenderType.MALE,
            displayMassUnit = MassUnit.KG,
        )
    }
}
