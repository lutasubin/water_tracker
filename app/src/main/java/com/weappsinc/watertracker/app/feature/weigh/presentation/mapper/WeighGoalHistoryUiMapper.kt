package com.weappsinc.watertracker.app.feature.weigh.presentation.mapper

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoal
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryRowUi
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryUiState
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

object WeighGoalHistoryUiMapper {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())

    fun map(goals: List<WeighCompletedGoal>, unit: MassUnit): WeighGoalHistoryUiState {
        if (goals.isEmpty()) return WeighGoalHistoryUiState.Empty(displayUnit = unit)
        return WeighGoalHistoryUiState.Content(
            displayUnit = unit,
            rows = goals.map { g ->
                val deltaKg = (g.achievedBodyWeightKg - g.journeyStartWeightKg).toFloat()
                val completedDate = Instant.ofEpochMilli(g.completedAtMs)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                    .format(dateFormatter)
                WeighGoalHistoryRowUi(
                    id = g.id,
                    targetValueText = MassDisplay.formatTargetKg(g.targetWeightKg.toFloat(), unit),
                    achievedValueText = MassDisplay.formatTargetKg(g.achievedBodyWeightKg.toFloat(), unit),
                    deltaValueText = MassDisplay.formatSignedKgDelta(deltaKg, unit),
                    completedAtText = completedDate,
                )
            },
        )
    }
}
