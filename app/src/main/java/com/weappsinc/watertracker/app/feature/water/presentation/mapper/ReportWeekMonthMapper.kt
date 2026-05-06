package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportBarUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportMonthPagerUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportSummaryLabel
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportUiState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

object ReportWeekMonthMapper {
    private val axisDayFmt = DateTimeFormatter.ofPattern("d MMM")

    /** Số cửa sổ 7 ngày hợp lệ: index 0 = kết thúc hôm nay, lùi 7 ngày mỗi lần tới install. */
    fun maxMonthWindowIndex(today: LocalDate, install: LocalDate): Int {
        var maxIdx = 0
        while (true) {
            val nextEnd = today.minusDays(7L * (maxIdx + 1))
            if (nextEnd < install) break
            maxIdx++
        }
        return maxIdx
    }

    fun buildWeekState(
        anchor: LocalDate,
        goalMl: Int,
        totalsByDay: Map<Long, Int>,
        selectedBarIndex: Int?,
        locale: Locale,
    ): ReportUiState {
        val fmt = axisDayFmt.withLocale(locale)
        val mon = anchor.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val bars = (0L until 7L).map { off ->
            val d = mon.plusDays(off)
            val v = totalsByDay[d.toEpochDay()] ?: 0
            ReportBarUi(label = d.format(fmt), valueMl = v, epochDay = d.toEpochDay())
        }
        val sum = bars.sumOf { it.valueMl }
        val avg = if (bars.isNotEmpty()) sum / bars.size else 0
        val max = maxOf(goalMl, bars.maxOfOrNull { it.valueMl } ?: 0, 1)
        return ReportUiState(
            period = ReportPeriod.Week,
            anchorDateLabel = ReportUiMapper.headerLabel(anchor, locale),
            goalMl = goalMl,
            summaryLeftLabel = ReportSummaryLabel.AvgPerDay,
            summaryLeftValueMl = avg,
            summaryLeftHighlightPrimary = true,
            summaryRightLabel = ReportSummaryLabel.Total,
            summaryRightValueMl = sum,
            chartBars = bars,
            chartMaxMl = max,
            selectedBarIndex = selectedBarIndex,
            records = emptyList()
        )
    }

    /** Tab Month: luôn 7 cột (cũ → mới), neo theo ngày cài qua cửa sổ lùi 7 ngày. */
    fun buildSevenDayWindowState(
        windowEnd: LocalDate,
        goalMl: Int,
        totalsByDay: Map<Long, Int>,
        selectedBarIndex: Int?,
        windowIndex: Int,
        maxWindowIndex: Int,
        locale: Locale,
    ): ReportUiState {
        val fmt = axisDayFmt.withLocale(locale)
        val start = windowEnd.minusDays(6)
        val bars = (0L..6L).map { off ->
            val d = start.plusDays(off)
            val v = totalsByDay[d.toEpochDay()] ?: 0
            ReportBarUi(label = d.format(fmt), valueMl = v, epochDay = d.toEpochDay())
        }
        val sum = bars.sumOf { it.valueMl }
        val avg = sum / 7
        val max = maxOf(goalMl, bars.maxOfOrNull { it.valueMl } ?: 0, 1)
        val cnt = maxWindowIndex + 1
        val thumbW = if (cnt <= 0) 1f else 1f / cnt
        val thumbStart = if (cnt <= 1) 0f else (maxWindowIndex - windowIndex).toFloat() / cnt
        return ReportUiState(
            period = ReportPeriod.Month,
            anchorDateLabel = ReportUiMapper.sevenDayWindowHeader(start, windowEnd, locale),
            goalMl = goalMl,
            summaryLeftLabel = ReportSummaryLabel.AvgPerDay,
            summaryLeftValueMl = avg,
            summaryLeftHighlightPrimary = true,
            summaryRightLabel = ReportSummaryLabel.Total,
            summaryRightValueMl = sum,
            chartBars = bars,
            chartMaxMl = max,
            selectedBarIndex = selectedBarIndex,
            records = emptyList(),
            monthPager = ReportMonthPagerUi(
                windowIndex = windowIndex,
                maxWindowIndex = maxWindowIndex,
                canGoOlder = windowIndex < maxWindowIndex,
                canGoNewer = windowIndex > 0,
                thumbStartFraction = thumbStart,
                thumbWidthFraction = thumbW
            )
        )
    }
}
