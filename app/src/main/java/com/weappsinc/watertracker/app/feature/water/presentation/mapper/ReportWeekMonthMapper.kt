package com.weappsinc.watertracker.app.feature.water.presentation.mapper

import com.weappsinc.watertracker.app.core.constants.AppText
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportBarUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportMonthPagerUi
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportPeriod
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportUiState
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object ReportWeekMonthMapper {
    private val axisDayFmt = DateTimeFormatter.ofPattern("d MMM", java.util.Locale.US)

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
        selectedBarIndex: Int?
    ): ReportUiState {
        val mon = anchor.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        val bars = (0L until 7L).map { off ->
            val d = mon.plusDays(off)
            val v = totalsByDay[d.toEpochDay()] ?: 0
            ReportBarUi(label = d.format(axisDayFmt), valueMl = v, epochDay = d.toEpochDay())
        }
        val sum = bars.sumOf { it.valueMl }
        val avg = if (bars.isNotEmpty()) sum / bars.size else 0
        val max = maxOf(goalMl, bars.maxOfOrNull { it.valueMl } ?: 0, 1)
        return ReportUiState(
            period = ReportPeriod.Week,
            anchorDateLabel = ReportUiMapper.headerLabel(anchor),
            goalMl = goalMl,
            summaryLeftLabel = AppText.REPORT_SUMMARY_AVG_DAY,
            summaryLeftValueMl = avg,
            summaryLeftHighlightPrimary = true,
            summaryRightLabel = AppText.REPORT_SUMMARY_TOTAL,
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
        maxWindowIndex: Int
    ): ReportUiState {
        val start = windowEnd.minusDays(6)
        val bars = (0L..6L).map { off ->
            val d = start.plusDays(off)
            val v = totalsByDay[d.toEpochDay()] ?: 0
            ReportBarUi(label = d.format(axisDayFmt), valueMl = v, epochDay = d.toEpochDay())
        }
        val sum = bars.sumOf { it.valueMl }
        val avg = sum / 7
        val max = maxOf(goalMl, bars.maxOfOrNull { it.valueMl } ?: 0, 1)
        val cnt = maxWindowIndex + 1
        val thumbW = if (cnt <= 0) 1f else 1f / cnt
        val thumbStart = if (cnt <= 1) 0f else (maxWindowIndex - windowIndex).toFloat() / cnt
        return ReportUiState(
            period = ReportPeriod.Month,
            anchorDateLabel = ReportUiMapper.sevenDayWindowHeader(start, windowEnd),
            goalMl = goalMl,
            summaryLeftLabel = AppText.REPORT_SUMMARY_AVG_DAY,
            summaryLeftValueMl = avg,
            summaryLeftHighlightPrimary = true,
            summaryRightLabel = AppText.REPORT_SUMMARY_TOTAL,
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
