package com.weappsinc.watertracker.app.feature.weigh.presentation.mapper

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighCompletedGoalDetail
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighGoalHistoryDetailUiState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

object WeighGoalHistoryDetailUiMapper {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
    private val dayLabelFormatter = DateTimeFormatter.ofPattern("dd/MM", Locale.getDefault())

    fun map(detail: WeighCompletedGoalDetail, unit: MassUnit): WeighGoalHistoryDetailUiState {
        val goal = detail.goal ?: return WeighGoalHistoryDetailUiState.Empty(displayUnit = unit)
        val signedDeltaKg = (goal.achievedBodyWeightKg - goal.journeyStartWeightKg).toFloat()
        val targetDirection = goal.targetWeightKg - goal.journeyStartWeightKg
        val isNeutral = abs(signedDeltaKg) < 0.001f
        val isFavorable = if (isNeutral) true else signedDeltaKg * targetDirection >= 0.0
        val completedDate = Instant.ofEpochMilli(goal.completedAtMs)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(dateFormatter)
        val chartPoints = detail.logs
            .groupBy { it.epochDay }
            .toSortedMap()
            .map { (epochDay, logs) ->
                val latest = logs.maxBy { it.recordedAtMs }
                WeighHistoryChartPoint(epochDay = epochDay, weightKg = latest.weightKg.toFloat())
            }
            .ifEmpty {
                listOf(
                    WeighHistoryChartPoint(
                        epochDay = goal.completedAtEpochDay,
                        weightKg = goal.achievedBodyWeightKg.toFloat(),
                    ),
                )
            }
        val xLabels = chartPoints.map { LocalDate.ofEpochDay(it.epochDay).format(dayLabelFormatter) }
        return WeighGoalHistoryDetailUiState.Content(
            displayUnit = unit,
            targetValueText = MassDisplay.formatTargetKg(goal.targetWeightKg.toFloat(), unit),
            achievedValueText = MassDisplay.formatTargetKg(goal.achievedBodyWeightKg.toFloat(), unit),
            startWeightText = MassDisplay.formatTargetKg(goal.journeyStartWeightKg.toFloat(), unit),
            progressDeltaValueText = MassDisplay.formatSignedKgDelta(signedDeltaKg, unit),
            progressDeltaFavorable = isFavorable,
            progressDeltaNeutral = isNeutral,
            completedAtText = completedDate,
            chartPoints = chartPoints,
            xLabels = xLabels,
        )
    }
}
