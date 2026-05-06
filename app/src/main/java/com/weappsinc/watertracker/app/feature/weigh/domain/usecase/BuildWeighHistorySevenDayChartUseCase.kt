package com.weappsinc.watertracker.app.feature.weigh.domain.usecase

import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighLogEntry

/** Gom log trong cửa sổ 7 ngày → một điểm/kg mỗi ngày (lặp giá trị gần nhất nếu thiếu ngày). */
class BuildWeighHistorySevenDayChartUseCase {

    operator fun invoke(
        logsInWindow: List<WeighLogEntry>,
        todayEpochDay: Long
    ): List<WeighHistoryChartPoint> {
        val start = todayEpochDay - SIX_DAYS
        val byDay = logsInWindow
            .filter { it.epochDay in start..todayEpochDay }
            .groupBy { it.epochDay }
            .mapValues { (_, v) ->
                v.maxBy { it.recordedAtMs }.weightKg.toFloat()
            }
        var carry: Float? = null
        val out = ArrayList<WeighHistoryChartPoint>(7)
        for (d in start..todayEpochDay) {
            byDay[d]?.let { carry = it }
            val w = carry ?: continue
            out.add(WeighHistoryChartPoint(epochDay = d, weightKg = w))
        }
        return out
    }

    companion object {
        private const val SIX_DAYS = 6L
    }
}
