package com.weappsinc.watertracker.app.feature.weigh.presentation.screen

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import com.weappsinc.watertracker.app.core.theme.AppColors
import com.weappsinc.watertracker.app.core.theme.WeighDimens
import com.weappsinc.watertracker.app.feature.weigh.domain.model.WeighHistoryChartPoint
import java.util.Locale
import kotlin.math.max

/** Vẽ gradient, đường tím, điểm viền, nhãn kg hai đầu mút (theo mock). */
internal fun DrawScope.drawWeighHistoryLineChartContent(
    chartPoints: List<WeighHistoryChartPoint>,
    textMeasurer: TextMeasurer,
    labelStyle: TextStyle,
) {
    val accent = AppColors.WeighHistoryAccent
    val n = chartPoints.size
    val padL = WeighDimens.WeighTrendChartPadH.toPx()
    val padR = WeighDimens.WeighTrendChartPadH.toPx()
    val padB = WeighDimens.WeighTrendChartPadBottom.toPx()
    val plotTop = WeighDimens.WeighTrendChartPlotTop.toPx()
    val plotBottom = size.height - padB
    val cw = (size.width - padL - padR).coerceAtLeast(1f)
    val ch = (plotBottom - plotTop).coerceAtLeast(1f)
    val rOut = WeighDimens.WeighTrendPointOuter.toPx()
    val rIn = WeighDimens.WeighTrendPointInner.toPx()
    val gapLab = WeighDimens.WeighTrendValueLabelGap.toPx()
    if (n == 0) return
    if (n == 1) {
        val cx = padL + cw / 2f
        val w = chartPoints[0].weightKg
        val cy = plotTop + ch / 2f
        drawCircle(color = accent, radius = rOut, center = Offset(cx, cy))
        drawCircle(color = Color.White, radius = rIn, center = Offset(cx, cy))
        val s = String.format(Locale.US, "%.1f", w)
        val layout = textMeasurer.measure(AnnotatedString(s), labelStyle)
        val ly = (cy - layout.size.height - gapLab).coerceAtLeast(2f)
        drawText(layout, topLeft = Offset(cx - layout.size.width / 2, ly))
        return
    }
    val minW = chartPoints.minOf { it.weightKg } - 1f
    val maxW = chartPoints.maxOf { it.weightKg } + 1f
    val rng = max(maxW - minW, 0.5f)
    fun xAt(i: Int) = padL + cw * (i / (n - 1).toFloat())
    fun yAt(w: Float) = plotTop + ch * (1f - (w - minW) / rng)
    val pts = List(n) { i -> Offset(xAt(i), yAt(chartPoints[i].weightKg)) }
    drawPath(
        path = buildWeighHistorySmoothFillPath(pts, plotBottom),
        brush = Brush.verticalGradient(
            colors = listOf(AppColors.WeighHistoryChartFillTop, Color.Transparent),
            startY = plotTop,
            endY = plotBottom
        )
    )
    drawPath(
        path = buildWeighHistorySmoothLinePath(pts),
        color = accent,
        style = Stroke(
            width = WeighDimens.WeighTrendLineStroke.toPx(),
            cap = StrokeCap.Round
        )
    )
    for (i in 0 until n) {
        val c = Offset(pts[i].x, pts[i].y)
        drawCircle(color = accent, radius = rOut, center = c)
        drawCircle(color = Color.White, radius = rIn, center = c)
    }
    val s0 = String.format(Locale.US, "%.1f", chartPoints.first().weightKg)
    val s1 = String.format(Locale.US, "%.1f", chartPoints.last().weightKg)
    val l0 = textMeasurer.measure(AnnotatedString(s0), labelStyle)
    val l1 = textMeasurer.measure(AnnotatedString(s1), labelStyle)
    val y0 = (pts[0].y - l0.size.height - gapLab).coerceAtLeast(2f)
    val y1 = (pts[n - 1].y - l1.size.height - gapLab).coerceAtLeast(2f)
    drawText(l0, topLeft = Offset(pts[0].x - l0.size.width / 2, y0))
    drawText(l1, topLeft = Offset(pts[n - 1].x - l1.size.width / 2, y1))
}
