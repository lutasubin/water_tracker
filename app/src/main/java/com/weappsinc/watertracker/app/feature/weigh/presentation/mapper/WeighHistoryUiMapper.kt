package com.weappsinc.watertracker.app.feature.weigh.presentation.mapper

import com.weappsinc.watertracker.app.feature.weigh.domain.model.MassUnit
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry
import com.weappsinc.watertracker.app.feature.weigh.domain.util.MassDisplay
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighHistoryRowUi
import com.weappsinc.watertracker.app.feature.weigh.presentation.state.WeighHistoryUiState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.abs

object WeighHistoryUiMapper {

    private val timeFmt: DateTimeFormatter =
        DateTimeFormatter.ofPattern("HH:mm dd/MM/uuuu").withZone(ZoneId.systemDefault())

    private val dayLabelFmt: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM")

    private const val DELTA_EPS = 0.01f

    fun map(
        logsDesc: List<WeighLogEntry>,
        chartPoints: List<WeighHistoryChartPoint>,
        unit: MassUnit
    ): WeighHistoryUiState {
        val xLabels = chartPoints.map { p ->
            LocalDate.ofEpochDay(p.epochDay).format(dayLabelFmt)
        }
        val rows = logsDesc.mapIndexed { i, e ->
            val older = logsDesc.getOrNull(i + 1)
            val deltaKg = older?.let { (e.weightKg - it.weightKg).toFloat() }
            val badge = when {
                deltaKg == null || abs(deltaKg) < DELTA_EPS -> null
                else -> MassDisplay.formatSignedKgDelta(deltaKg, unit)
            }
            val inc = deltaKg != null && deltaKg > DELTA_EPS
            WeighHistoryRowUi(
                timeLabel = timeFmt.format(Instant.ofEpochMilli(e.recordedAtMs)),
                weightValueText = MassDisplay.formatTargetKg(e.weightKg.toFloat(), unit),
                deltaSignedText = badge,
                deltaIsIncrease = inc
            )
        }
        return WeighHistoryUiState(
            massUnit = unit,
            chartPoints = chartPoints,
            xLabels = xLabels,
            listRows = rows,
            hasChartData = chartPoints.isNotEmpty()
        )
    }
}
