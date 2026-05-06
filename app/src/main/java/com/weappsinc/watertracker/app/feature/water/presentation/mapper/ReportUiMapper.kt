package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.feature.water.domain.model.WaterIntakeLog
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportBarUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportRecordUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportUiState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object ReportUiMapper {
    private val headerFmt = DateTimeFormatter.ofPattern("MMM d, yyyy", java.util.Locale.US)
    private val axisDayFmt = DateTimeFormatter.ofPattern("d MMM", java.util.Locale.US)
    private val recordTimeFmt = DateTimeFormatter.ofPattern("hh:mm a", java.util.Locale.US)

    val dayBucketLabels: List<String> =
        listOf("00-04", "04-08", "08-12", "12-16", "16-20", "20-24")

    fun headerLabel(anchor: LocalDate): String = anchor.format(headerFmt)

    fun weekEpochRange(anchor: LocalDate): Pair<Long, Long> {
        val mon = anchor.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
        val sun = mon.plusDays(6)
        return mon.toEpochDay() to sun.toEpochDay()
    }

    fun sevenDayWindowEpochRange(windowEnd: LocalDate): Pair<Long, Long> {
        val start = windowEnd.minusDays(6)
        return start.toEpochDay() to windowEnd.toEpochDay()
    }

    fun sevenDayWindowHeader(start: LocalDate, end: LocalDate): String {
        val locale = java.util.Locale.US
        val endStr = end.format(DateTimeFormatter.ofPattern("MMM d, yyyy", locale))
        val startPat =
            if (start.year == end.year) "MMM d" else "MMM d, yyyy"
        val startStr = start.format(DateTimeFormatter.ofPattern(startPat, locale))
        return "$startStr – $endStr"
    }

    fun buildDayState(
        anchor: LocalDate,
        goalMl: Int,
        dayTotalMl: Int,
        logs: List<WaterIntakeLog>,
        bucketMl: List<Int>,
        zone: ZoneId,
        selectedBarIndex: Int?
    ): ReportUiState {
        val bars = dayBucketLabels.zip(bucketMl) { a, b -> ReportBarUi(label = a, valueMl = b) }
        val max = maxOf(goalMl, bars.maxOfOrNull { it.valueMl } ?: 0, 1)
        val records = logs.sortedByDescending { it.timestampMs }.map { log ->
            val z = Instant.ofEpochMilli(log.timestampMs).atZone(zone)
            ReportRecordUi(timeLabel = z.format(recordTimeFmt), amountMl = log.amountMl)
        }
        return ReportUiState(
            period = ReportPeriod.Day,
            anchorDateLabel = headerLabel(anchor),
            goalMl = goalMl,
            summaryLeftLabel = AppText.REPORT_SUMMARY_TODAY,
            summaryLeftValueMl = dayTotalMl,
            summaryLeftHighlightPrimary = true,
            summaryRightLabel = AppText.REPORT_SUMMARY_GOAL,
            summaryRightValueMl = goalMl,
            chartBars = bars,
            chartMaxMl = max,
            selectedBarIndex = selectedBarIndex,
            records = records
        )
    }
}
