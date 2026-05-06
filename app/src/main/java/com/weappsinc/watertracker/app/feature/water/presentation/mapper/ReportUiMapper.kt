package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.feature.water.domain.model.WaterIntakeLog
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportBarUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportRecordUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportSummaryLabel
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportUiState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object ReportUiMapper {
    private val headerFmt = DateTimeFormatter.ofPattern("MMM d, yyyy")
    private val axisDayFmt = DateTimeFormatter.ofPattern("d MMM")
    private val recordTimeFmt = DateTimeFormatter.ofPattern("hh:mm a")

    fun headerLabel(anchor: LocalDate, locale: java.util.Locale): String =
        anchor.format(headerFmt.withLocale(locale))

    fun weekEpochRange(anchor: LocalDate): Pair<Long, Long> {
        val mon = anchor.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
        val sun = mon.plusDays(6)
        return mon.toEpochDay() to sun.toEpochDay()
    }

    fun sevenDayWindowEpochRange(windowEnd: LocalDate): Pair<Long, Long> {
        val start = windowEnd.minusDays(6)
        return start.toEpochDay() to windowEnd.toEpochDay()
    }

    fun sevenDayWindowHeader(start: LocalDate, end: LocalDate, locale: java.util.Locale): String {
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
        selectedBarIndex: Int?,
        dayBucketLabels: List<String>,
        locale: java.util.Locale,
    ): ReportUiState {
        val recordFmt = recordTimeFmt.withLocale(locale)
        val bars = dayBucketLabels.zip(bucketMl) { a, b -> ReportBarUi(label = a, valueMl = b) }
        val max = maxOf(goalMl, bars.maxOfOrNull { it.valueMl } ?: 0, 1)
        val records = logs.sortedByDescending { it.timestampMs }.map { log ->
            val z = Instant.ofEpochMilli(log.timestampMs).atZone(zone)
            ReportRecordUi(timeLabel = z.format(recordFmt), amountMl = log.amountMl)
        }
        return ReportUiState(
            period = ReportPeriod.Day,
            anchorDateLabel = headerLabel(anchor, locale),
            goalMl = goalMl,
            summaryLeftLabel = ReportSummaryLabel.Today,
            summaryLeftValueMl = dayTotalMl,
            summaryLeftHighlightPrimary = true,
            summaryRightLabel = ReportSummaryLabel.Goal,
            summaryRightValueMl = goalMl,
            chartBars = bars,
            chartMaxMl = max,
            selectedBarIndex = selectedBarIndex,
            records = records
        )
    }
}
