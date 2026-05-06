package com.weappsinc.watertracker.app.feature.water.presentation.report

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.feature.water.presentation.state.ReportBarUi
import kotlin.math.max
import kotlin.math.min

/** Vẽ lưới, cột, nhãn trục X và tooltip lên [DrawScope] của biểu đồ Report. */
internal fun DrawScope.drawReportChartContent(
    textMeasurer: TextMeasurer,
    yTicks: List<Int>,
    yTop: Int,
    bars: List<ReportBarUi>,
    selectedIndex: Int?,
    axisStyle: TextStyle,
    axisSelStyle: TextStyle,
    tooltipStyle: TextStyle,
    yAxisPx: Float,
    endPadPx: Float,
    bottomPadPx: Float,
    topPadPx: Float,
    plotTopExtraPx: Float,
    unitMlSuffix: String,
) {
    val n = bars.size.coerceAtLeast(1)
    val plotLeft = yAxisPx
    val plotRight = size.width - endPadPx
    val plotBottom = size.height - bottomPadPx
    val plotTop = topPadPx + plotTopExtraPx
    val plotH = plotBottom - plotTop
    val gridColor = AppColors.HomeMuted.copy(alpha = 0.38f)
    yTicks.forEach { tick ->
        val y = plotBottom - (tick.toFloat() / yTop) * plotH
        drawReportDashedHLine(y, plotLeft, plotRight, gridColor)
        val ly = textMeasurer.measure(
            AnnotatedString(ReportChartAxisUtils.formatYLabel(tick)),
            axisStyle
        )
        drawText(ly, topLeft = Offset(0f, y - ly.size.height / 2))
    }
    val slotW = (plotRight - plotLeft) / n
    val barW = min(slotW * 0.42f, 28.dp.toPx())
    bars.forEachIndexed { i, bar ->
        val cx = plotLeft + (i + 0.5f) * slotW
        val h = (bar.valueMl.toFloat() / yTop) * plotH
        val top = plotBottom - h
        drawPillBar(cx - barW / 2f, top, barW, plotBottom, AppColors.HomePrimary)
    }
    val xPad = 6.dp.toPx()
    val xLineGap = 2.dp.toPx()
    bars.forEachIndexed { i, bar ->
        val cx = plotLeft + (i + 0.5f) * slotW
        val (a, b) = ReportChartAxisUtils.splitXLabel(bar.label)
        val style = if (i == selectedIndex) axisSelStyle else axisStyle
        val l1 = textMeasurer.measure(AnnotatedString(a), style)
        val l2 = if (b.isNotEmpty()) textMeasurer.measure(AnnotatedString(b), style) else null
        val tw = max(l1.size.width, l2?.size?.width ?: 0)
        val y0 = plotBottom + xPad
        drawText(l1, topLeft = Offset(cx - tw / 2 + (tw - l1.size.width) / 2, y0))
        if (l2 != null) {
            drawText(
                l2,
                topLeft = Offset(cx - tw / 2 + (tw - l2.size.width) / 2, y0 + l1.size.height + xLineGap)
            )
        }
    }
    if (selectedIndex != null && selectedIndex in bars.indices) {
        val bar = bars[selectedIndex]
        val cx = plotLeft + (selectedIndex + 0.5f) * slotW
        val h = (bar.valueMl.toFloat() / yTop) * plotH
        val topB = plotBottom - h
        val tip = "${bar.valueMl}$unitMlSuffix"
        val tl = textMeasurer.measure(AnnotatedString(tip), tooltipStyle)
        val pad = 8.dp.toPx()
        val rw = tl.size.width + pad * 2
        val rh = tl.size.height + pad * 2
        val rx = (cx - rw / 2f).coerceIn(plotLeft, plotRight - rw)
        val ry = (topB - rh - 8.dp.toPx()).coerceAtLeast(topPadPx)
        drawRoundRect(
            color = AppColors.HomeTitle,
            topLeft = Offset(rx, ry),
            size = Size(rw, rh),
            cornerRadius = CornerRadius(8.dp.toPx(), 8.dp.toPx())
        )
        drawText(tl, color = Color.White, topLeft = Offset(rx + pad, ry + pad))
    }
}
